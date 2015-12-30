package es.ateneasystems.arduinotest.global;

import android.app.Application;

/**
 * Created by cubel on 27/12/15.
 */
public class Globales extends Application {

    private String arduino_conectado;
    private String arduino_conectado_mac;


    //Getters
    public String getArduino_conectado() {

        return this.arduino_conectado;
    }

    public String getArduino_conectado_mac() {
        return this.arduino_conectado_mac;
    }

    //Setters
    public void setArduino_conectado(String arduino_conectado) {
        this.arduino_conectado = arduino_conectado;
    }

    public void setArduino_conectado_mac(String arduino_conectado_mac) {
        this.arduino_conectado_mac = arduino_conectado_mac;
    }
}