package com.pabloespana.proyectochat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class NuevoChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_chat);
        LinearLayout l = (LinearLayout) findViewById(R.id.disponible);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ChatActivity.class);
                intent.putExtra("Direccion","");
                intent.putExtra("Nombre", "Francisco Bermello");
                startActivityForResult(intent, 0);
            }
        });
    }
}
