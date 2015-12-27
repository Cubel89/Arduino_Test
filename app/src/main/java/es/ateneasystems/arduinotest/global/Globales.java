package es.ateneasystems.arduinotest.global;

import android.app.Application;

/**
 * Created by cubel on 27/12/15.
 */
public class Globales extends Application {

    private boolean bluetooth_state = false;

    public boolean getBluetooth_state(){
        return bluetooth_state;
    }

    public void setBluetooth_state(boolean bluetooth_state) {
        this.bluetooth_state = bluetooth_state;
    }
}
