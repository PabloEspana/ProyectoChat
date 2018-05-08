package com.pabloespana.proyectochat;


import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ContactosFragment extends Fragment {
    Set<BluetoothDevice> Dispositivos;
    ListView Listado;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactos, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton b = (FloatingActionButton) getView().findViewById(R.id.btnBuscarDispositivos);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DispositivosActivity.class);
                getActivity().startActivity(intent);
            }
        });
        ListarContactos();
    }

    public void ListarContactos(){
        Dispositivos = new BluetoothConnect().getListContactBluetoh();
        Listado = (ListView) getView().findViewById(R.id.listaContacto);
        List<String> ListaDispositivos = new ArrayList<String>();
        for (BluetoothDevice device : Dispositivos){
            ListaDispositivos.add(device.getName()+"\n"+device.getAddress());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, ListaDispositivos );
        Listado.setAdapter(arrayAdapter);
    }
}
