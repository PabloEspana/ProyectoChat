package com.pabloespana.proyectochat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.Set;

public class BluetoohConnect {

    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> Dispositivos;
    private BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

    public BluetoohConnect() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    // Comprueba de que existe bluetooth en el dispositivo
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
