package com.pabloespana.proyectochat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    //En estas variables se guardan la direccion y nombre del
    // dispositivo al que se le enviará el mensaje
    String BTAddres, BTName, BTLocalName="";
    ImageButton btnSend;
    EditText txtMsg;
    LinearLayout linearLayout;
    BluetoothAdapter BTAdapter;
    BluetoothDevice dispositivo;
    SendRecive sendRecive;
    Set<BluetoothDevice> Dispositivos;
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    int REQUEST_ENABLE_BLUETOOH=1;
    private static String APP_NAME = "Proyecto Chat";
    private  static UUID MY_UUID = UUID.fromString("5841695d-8715-4a8e-b380-6965e002ee97");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        //Se obtiene los parametros Extras enviados desde otro activity
        //y se guarda en las variables correspondientes
        BTAddres = getIntent().getStringExtra("Direccion");
        BTName = getIntent().getStringExtra("Nombre");
        linearLayout =(LinearLayout)findViewById(R.id.layoutScrollChat);
        btnSend =(ImageButton)findViewById(R.id.buttonSend);
        txtMsg = (EditText)findViewById(R.id.txtMsg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMsg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(BTName);
        BluetoothConnect BT =  new BluetoothConnect();
        BTAdapter = BT.getBluetoothAdapter();
        if(!BTAdapter.isEnabled()){
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BLUETOOH);
        }
        BTLocalName = BTAdapter.getName();
        Dispositivos = BT.getListContactBluetooth();
        ServerClass servidor = null;
        try {
            servidor = new ServerClass();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(servidor!=null) {
            servidor.start();
            Log.d("Servidor","Conectando");
        }
        for (BluetoothDevice device : Dispositivos){
            if(device.getAddress().equals(BTAddres)){
                BTName = device.getName();
                dispositivo = device;
                break;
            }
        }
        ClientClass cliente;
        if (dispositivo!=null) {
            cliente = new ClientClass(dispositivo);
            cliente.start();
            Log.d("Cliente","Conectando");
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text =  txtMsg.getText().toString() ;//"Hola mundo";
                if (text.equals("")){
                    Log.i("Mensaje","Vacío");
                    return;
                }

                Log.i("Mensaje",text);
                sendRecive.write(text.getBytes());
            }
        });
    }

    // Regresa a la pantalla anterior al presionar la flecha
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
/*
    private class Thread2 extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }
    */
    @SuppressLint("ResourceAsColor")
    public void ActualizarAmbasPantalla(String msg){
        TextView valueTV = new TextView(getApplicationContext());
        valueTV.setText(msg);
        valueTV.setTextColor(R.color.Black);
        valueTV.setTextSize(16);
        //valueTV.setTextColor(Color.parseColor("#FF0000"));
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.addView(valueTV);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("ResourceAsColor")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case STATE_LISTENING:
                    Log.d("STATE_LISTENING","STATE_LISTENING");
                    break;
                case STATE_CONNECTING:
                    Log.d("STATE_LISTENING","STATE_CONNECTING");
                    break;
                case STATE_CONNECTED:
                    Log.d("STATE_LISTENING","STATE_CONNECTED");
                    break;
                case STATE_CONNECTION_FAILED:
                    Log.d("STATE_LISTENING","STATE_CONNECTION_FAILED");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMSG = new String(readBuff,0,msg.arg1);
                    String MSG = BTName+" dice:\n"+tempMSG;
                    ActualizarAmbasPantalla(MSG);
                    //txtMsg.setText("");
                    break;
            }
            return true;
        }
    });


    private class ServerClass extends Thread
    {
        private BluetoothServerSocket serverSocket;
        public ServerClass () throws IOException {
            try {
                serverSocket = BTAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }

        public void run()
        {
            BluetoothSocket socket = null;
            while(socket == null){
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                }catch (IOException ex){
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                    ex.printStackTrace();
                }

                if (socket!=null){
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
                    sendRecive = new SendRecive(socket);
                    sendRecive.start();
                    break;
                }
            }
        }
    }


    private class ClientClass extends Thread
    {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice dev){
            device = dev;
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);
                socket.connect();
                sendRecive = new SendRecive(socket);
                sendRecive.start();
                Log.i("Cliente ","Dispositivo Conectado");
            } catch (IOException e) {
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
                e.printStackTrace();
            }
        }
    }


    private class SendRecive extends  Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendRecive(BluetoothSocket socket){
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true){
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
                String MSG = BTLocalName+" dice:\n"+(txtMsg.getText().toString());
                ActualizarAmbasPantalla(MSG);
                txtMsg.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
