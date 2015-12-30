package es.ateneasystems.arduinotest.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cubel on 27/12/15.
 */
public class Globales extends Application {

    private boolean aviso_cookies;

    private String arduino_conectado;
    private String arduino_conectado_mac;

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
    }



    //Getters
    public String getArduino_conectado() {

        return this.arduino_conectado;
    }

    public String getArduino_conectado_mac() {
        return this.arduino_conectado_mac;
    }

    public boolean getAviso_cookies() {
        return this.aviso_cookies;
    }

    //Setters
    public void setArduino_conectado(String arduino_conectado) {
        this.arduino_conectado = arduino_conectado;
    }

    public void setArduino_conectado_mac(String arduino_conectado_mac) {
        this.arduino_conectado_mac = arduino_conectado_mac;
    }

    public void setAviso_cookies(boolean aviso_cookies) {
        //Editamos las preferencias
        editor.putBoolean("aviso_cookies", aviso_cookies);
        editor.commit();

        this.aviso_cookies = aviso_cookies;
    }
}