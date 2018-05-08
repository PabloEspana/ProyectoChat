package com.pabloespana.proyectochat;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    BluetoothConnect bluetoothConnect = new BluetoothConnect();
    private SectionsPageAdapter sPageAdapter;
    private ViewPager vP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comprobarBluetooth();
        bluetoothConnect.habilitarBluetooth();
        setContentView(R.layout.activity_main);

        sPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        vP = (ViewPager) findViewById(R.id.viewpager);
        configurarViewPager(vP);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(vP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothConnect.habilitarBluetooth();  // volver a activar bluetooth
    }


    // Este método configura el menú de la aplicación
    private void configurarViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatsFragment(), "Chats");
        adapter.addFragment(new GruposFragment(), "Grupos");
        adapter.addFragment(new ContactosFragment(), "Contactos");
        viewPager.setAdapter(adapter);
    }


    // Mostrará mensaje de error y cerrará la app si no soporta bluetooth
    public void comprobarBluetooth(){
        boolean soportaBluetooth =  new BluetoothConnect().comprobarBluetooth();
        if (!soportaBluetooth) {
            new AlertDialog.Builder(this)
                    .setTitle("Lo sentimos!")
                    .setMessage("Su dispositivo no soporta Bluetooth")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    }).show();
        }
    }
}
