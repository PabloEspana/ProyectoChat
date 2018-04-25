package com.pabloespana.proyectochat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.Set;

public class BluetoohConnect {
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> Dispositivos;

    public BluetoohConnect() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }
    //Funcion que retorna la lista de dispositivos emparejados.
    public Set<BluetoothDevice> getListContactBluetoh (){
        Dispositivos =  this.bluetoothAdapter.getBondedDevices();
        return Dispositivos;
    }
}
