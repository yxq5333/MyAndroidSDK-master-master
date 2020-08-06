package com.crazyhuskar.myandroidsdk.util.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.crazyhuskar.myandroidsdk.util.MyUtilThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.content.Context.BLUETOOTH_SERVICE;

/**
 * @author CrazyHuskar
 * @date 2018-11-08
 */
public class MyUtilBluetooth {
    private static MyUtilBluetooth instance;
    private Activity activity;
    private boolean isOpen = false;

    private BluetoothManager bnluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket clientSocket;

    private MyBluetoothInitCallback myBluetoothInitCallback;
    private MyBluetoothScanCallback myBluetoothScanCallback;
    private MyBluetoothGattCallback myBluetoothGattCallback;
    private MyBluetoothSocketCallback myBluetoothSocketCallback;

    public MyUtilBluetooth(Activity activity) {
        this.activity = activity;
    }

    public static MyUtilBluetooth getInstance(Activity activity) {
        if (null == instance) {
            instance = new MyUtilBluetooth(activity);
        }
        return instance;
    }

    /**
     * 初始化蓝牙
     *
     * @param callback
     * @return
     */
    @SuppressLint("MissingPermission")
    public boolean initBluetooth(MyBluetoothInitCallback callback) {
        myBluetoothInitCallback = callback;
        bnluetoothManager = (BluetoothManager) activity.getSystemService(BLUETOOTH_SERVICE);
        if (bnluetoothManager != null) {
            bluetoothAdapter = bnluetoothManager.getAdapter();
        } else {
            return false;
        }
        if (!checkBluetoothSupport(bluetoothAdapter)) {
            return false;
        }
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        activity.registerReceiver(mBluetoothReceiver, filter);
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        } else {
            myBluetoothInitCallback.onStart();
        }
        return true;
    }

    /**
     * 注销蓝牙开关广播
     */
    public void unregisterBluetooth() {
        activity.unregisterReceiver(mBluetoothReceiver);
    }


    /**
     * 搜索蓝牙设备
     *
     * @param callback
     */
    @SuppressLint("MissingPermission")
    public void startScanDevice(MyBluetoothScanCallback callback) {
        myBluetoothScanCallback = callback;
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.setPriority(Integer.MAX_VALUE);//设置优先级
        activity.registerReceiver(receiver, filter);
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }

    /**
     * 停止搜索
     */
    @SuppressLint("MissingPermission")
    public void stopScanDevice() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    /**
     * BLE连接监听设备
     *
     * @param device
     * @param callback
     * @return
     */
    public BluetoothGatt connectDeviceByGatt(BluetoothDevice device, MyBluetoothGattCallback callback) {
        myBluetoothGattCallback = callback;
        return device.connectGatt(activity, false, mGattCallback);
    }

    /**
     * 经典蓝牙连接
     *
     * @param device
     * @param uuid
     * @param callback
     */
    @SuppressLint("MissingPermission")
    public void connectDeviceBySocket(BluetoothDevice device, UUID uuid, final MyBluetoothSocketCallback callback) {
        myBluetoothSocketCallback = callback;
        try {
            clientSocket = device.createRfcommSocketToServiceRecord(uuid);
            clientSocket.connect();
            MyUtilThreadPool.getInstance().scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;
                    try {
                        InputStream in = clientSocket.getInputStream();
                        //一直循环接收处理消息*
                        if ((bytes = in.read(buffer)) != 0) {
                            byte[] buf_data = new byte[bytes];
                            System.arraycopy(buffer, 0, buf_data, 0, bytes);
                            String msg = new String(buf_data, "utf-8");
                            callback.read(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 经典拉亚发送数据
     *
     * @param bytes
     */
    public void sendDataBySocket(byte[] bytes) {
        if (clientSocket != null && clientSocket.isConnected()) {
            try {
                clientSocket.getOutputStream().write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭经典蓝牙
     */
    public void closedBySocket() {
        if (clientSocket != null && clientSocket.isConnected()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                myBluetoothGattCallback.isConnected();
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                myBluetoothGattCallback.isDisConnected();
                gatt.close();
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                myBluetoothGattCallback.isConnecting();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {
                myBluetoothGattCallback.isDisConnecting();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            myBluetoothGattCallback.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            myBluetoothGattCallback.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            myBluetoothGattCallback.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            myBluetoothGattCallback.onCharacteristicChanged(gatt, characteristic);
        }

    };

    /**
     * 获取历史匹配记录
     */
    @SuppressLint("MissingPermission")
    public List<BluetoothDevice> getBondedDevices() {
        List<BluetoothDevice> deviceList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            deviceList.addAll(pairedDevices);
        }
        return deviceList;
    }

    /**
     * 蓝牙开关状态广播
     */
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
            switch (state) {
                case BluetoothAdapter.STATE_ON:
                    isOpen = true;
                    myBluetoothInitCallback.onStart();
                    break;
                case BluetoothAdapter.STATE_OFF:
                    isOpen = false;
                    myBluetoothInitCallback.onStop();
                    break;
                default:
            }

        }
    };
    /**
     * 蓝牙搜索广播
     */
    @SuppressLint("MissingPermission")
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    myBluetoothScanCallback.onScan(device);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                myBluetoothScanCallback.onStop();
            }
        }
    };

    /**
     * 判断是否支持蓝牙
     *
     * @param bluetoothAdapter
     * @return
     */
    private boolean checkBluetoothSupport(BluetoothAdapter bluetoothAdapter) {
        if (bluetoothAdapter == null) {
            MyUtilLog.e("不支持经典蓝牙");
            return false;
        }
        if (!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            MyUtilLog.e("不支持低功耗蓝牙");
            return false;
        }
        return true;
    }
}
