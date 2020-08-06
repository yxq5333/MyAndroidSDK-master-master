package com.crazyhuskar.myandroidsdk.util.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * @author CrazyHuskar
 * @date 2018-11-08
 */
public interface MyBluetoothGattCallback {
    void isConnected();

    void isDisConnected();

    void isConnecting();

    void isDisConnecting();

    void onServicesDiscovered(BluetoothGatt gatt, int status);

    void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

    void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

    void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);
}
