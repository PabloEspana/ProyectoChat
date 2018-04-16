package com.pabloespana.proyectochat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class DispositivosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        // Adding Toolbar to Main screen
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDispositivos);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }*/
}
