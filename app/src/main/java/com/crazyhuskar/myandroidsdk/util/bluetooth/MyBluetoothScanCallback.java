package com.crazyhuskar.myandroidsdk.util.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * @author CrazyHuskar
 * @date 2018-11-08
 */
public interface MyBluetoothScanCallback {
    void onScan(BluetoothDevice device);

    void onStop();
}
