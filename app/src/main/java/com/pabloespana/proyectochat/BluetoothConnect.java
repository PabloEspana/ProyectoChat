package com.pabloespana.proyectochat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Set;

public class BluetoothConnect {

    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> Dispositivos;
    private BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

    public BluetoothConnect() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    // Comprueba que el dispositivo admite bluetooth
    public boolean comprobarBluetooth(){
        boolean soportaBluetooth = true;
        if ( bluetooth == null) {
            soportaBluetooth = false;
        }
        return soportaBluetooth;
    }

    // Hsabilita el bluetooth en caso de no estarlo
    public void habilitarBluetooth(){
        if (!bluetooth.isEnabled()){
            bluetooth.enable();
        }
    }

    // Retorna la lista de dispositivos emparejados.
    public Set<BluetoothDevice> getListContactBluetoh (){
        Dispositivos =  this.bluetoothAdapter.getBondedDevices();
        return Dispositivos;
    }

}
