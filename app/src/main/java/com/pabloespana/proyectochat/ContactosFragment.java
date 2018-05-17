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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class ContactosFragment extends Fragment implements AbsListView.OnItemClickListener{

    Set<BluetoothDevice> Dispositivos;
    ArrayList<BTDevice> DevicesList = new ArrayList<BTDevice>();
    ListView Listado;
    List<String> ListaDispositivos = new ArrayList<String>();

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
        ListaDispositivos.clear();
        Dispositivos = new BluetoothConnect().getListContactBluetooth();
        Listado = (ListView) getView().findViewById(R.id.listaContacto);
        DevicesList.clear();

        for (BluetoothDevice device : Dispositivos){
            DevicesList.add(new BTDevice(device,false, R.drawable.foto1));
            ListaDispositivos.add(device.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, ListaDispositivos )
        { // Muestra la lista de dispositivos con su una imagen
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                Random rgenerador = new Random();
                Integer[] imagenesID = {R.drawable.foto1, R.drawable.foto2, R.drawable.foto3,};
                int resource = imagenesID[rgenerador.nextInt(imagenesID.length)];
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View item = inflater.inflate(R.layout.list_contactos, null);
                TextView textView1 = (TextView)item.findViewById(R.id.nombre);
                textView1.setText(ListaDispositivos.get(position));
                ImageView imageView1 = (ImageView)item.findViewById(R.id.foto);
                imageView1.setImageResource(resource);
                return(item);
            }
        };
        Listado.setAdapter(arrayAdapter);
        Listado.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BTDevice dev = DevicesList.get(i);
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("Direccion",dev.getAddress());
        intent.putExtra("Nombre",dev.getDeviceName());
        getActivity().startActivity(intent);
    }
}
