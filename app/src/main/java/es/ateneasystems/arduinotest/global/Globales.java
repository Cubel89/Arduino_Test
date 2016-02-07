package es.ateneasystems.arduinotest.global;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by cubel on 27/12/15.
 */
public class Globales extends Application {

    private boolean aviso_cookies;

    private String arduino_conectado;
    private String arduino_conectado_mac;

    private Boolean publicidad;


    //################ Bluetooth ################//
    private BluetoothAdapter bluetoothDispositivoAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    //Variables estaticas
    public static int REQUEST_BLUETOOTH = 1;


    public void setbluetoothDispositivoAdapter(BluetoothAdapter bluetoothDispositivoAdapter) {

        this.bluetoothDispositivoAdapter = bluetoothDispositivoAdapter;

    }

    public BluetoothAdapter getbluetoothDispositivoAdapter() {

        return this.bluetoothDispositivoAdapter;
    }

    public void setmNewDevicesArrayAdapter (ArrayAdapter mNewDevicesArrayAdapter){

        this.mNewDevicesArrayAdapter = mNewDevicesArrayAdapter;

    }

    public ArrayAdapter getmNewDevicesArrayAdapter (){

        return this.mNewDevicesArrayAdapter;

    }

    public int getRequestBluetooth(){

        return  this.REQUEST_BLUETOOTH;

    }

    //################ Bluetooth ################//
    //################ Bluetooth 2 ################//
    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");
    final static int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothSocket btSocket = null;
    //private StringBuilder sb = new StringBuilder();

    //private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public SimpleDateFormat getTimeformat() {
        return this.timeformat;
    }

    public int getRECIEVE_MESSAGE() {
        return this.RECIEVE_MESSAGE;
    }

    public void setBtSocket(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }

    public BluetoothSocket getBtSocket() {
        return this.btSocket;
    }

    public UUID getMyUuid() {
        return this.MY_UUID;
    }


    //################ Bluetooth 2 ################//

    //Preferencias
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    //Constructor
    public void onCreate() {
        super.onCreate();

        //Cargamos las preferencias
        prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE); //Para poder leer
        editor = prefs.edit(); //Para poder guardar

        //Cargamos el contenido de las preferencias
        this.aviso_cookies = prefs.getBoolean("aviso_cookies", false);

        //Iniciamos la publicidad a no
        this.publicidad = false;
    }



    //Getters
    public boolean getPublicidad() {
        return this.publicidad;
    }
    public boolean getAviso_cookies() {
        return this.aviso_cookies;
    }

    public String getArduino_conectado() {

        return this.arduino_conectado;
    }

    public String getArduino_conectado_mac() {
        return this.arduino_conectado_mac;
    }



    //Setters
    public void setPublicidad(boolean publicidad) {
        this.publicidad = publicidad;
    }
    public void setAviso_cookies(boolean aviso_cookies) {
        //Editamos las preferencias
        editor.putBoolean("aviso_cookies", aviso_cookies);
        editor.commit();

        this.aviso_cookies = aviso_cookies;
    }

    public void setArduino_conectado(String arduino_conectado) {
        this.arduino_conectado = arduino_conectado;
    }

    public void setArduino_conectado_mac(String arduino_conectado_mac) {
        this.arduino_conectado_mac = arduino_conectado_mac;
    }




}

