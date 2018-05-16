package com.pabloespana.proyectochat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class activity_new_chat extends AppCompatActivity{
    //En estas variables se guardan la direccion y nombre del
    // dispositivo al que se le enviará el mensaje
    String BTAddres,BTName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        //Se obtiene los parametros Extras enviados desde otro activity
        //y se guarda en las variables correspondientes
        BTAddres = getIntent().getStringExtra("Direccion");
        BTName = getIntent().getStringExtra("Nombre");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMsg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(BTName);
    }

    // Regresa a la pantalla anterior al presionar la flecha
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
