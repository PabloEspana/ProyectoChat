package com.pabloespana.proyectochat;

import android.bluetooth.BluetoothDevice;

public class BTDevice {
    private BluetoothDevice Dispositivo;
    private String deviceName;
    private String address;
    private boolean connected;

    public BTDevice(BluetoothDevice dispositivo,boolean connect) {
        this.Dispositivo = dispositivo;
        this.deviceName = dispositivo.getName();
        this.address = dispositivo.getAddress();
        this.connected = connect;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean getConnected() {
        return connected;
    }

    public String getAddress() {
        return address;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public BluetoothDevice getDevice(){
        return Dispositivo;
    }
}
