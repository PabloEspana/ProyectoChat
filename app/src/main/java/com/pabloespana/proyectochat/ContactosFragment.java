package com.pabloespana.proyectochat;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ContactosFragment extends Fragment implements AbsListView.OnItemClickListener{

    Set<BluetoothDevice> Dispositivos;
    ArrayList<BTDevice> DevicesList;
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

        Dispositivos = new BluetoothConnect().getListContactBluetooth();
        Listado = (ListView) getView().findViewById(R.id.listaContacto);
        DevicesList = new ArrayList<BTDevice>();
        List<String> ListaDispositivos = new ArrayList<String>();

        for (BluetoothDevice device : Dispositivos){
            DevicesList.add(new BTDevice(device,false));
            ListaDispositivos.add(device.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, ListaDispositivos )
        { // En este metodo se soluciona el color de texto
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        Listado.setAdapter(arrayAdapter);
        Listado.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BTDevice dev = DevicesList.get(i);
        Intent intent = new Intent(getActivity(), activity_new_chat.class);
        intent.putExtra("Direccion",dev.getAddress());
        intent.putExtra("Nombre",dev.getDeviceName());
        getActivity().startActivity(intent);
    }
}
