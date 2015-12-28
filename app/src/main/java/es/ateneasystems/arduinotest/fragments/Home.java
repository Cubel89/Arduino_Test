package es.ateneasystems.arduinotest.fragments;


import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.ateneasystems.arduinotest.R;
import es.ateneasystems.arduinotest.dialog.DialogoAceptarCancelar;


public class Home extends Fragment {
    /**
     * Log
     */
    private String logname = "Home (Fragment)";

    //Variables estaticas
    public static int REQUEST_BLUETOOTH = 1;

    //Variables
    private View view;
    BluetoothAdapter bluetoothDispostivo;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Declaracion de componentes
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        bluetoothDispostivo = BluetoothAdapter.getDefaultAdapter();



        //Pulsaciones de botones
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_cartel("Botón no funcional");
                //comprobar_bluetooth();
                //habilitar_bluetooth();

            }
        });

        //Devolvemos la vista
        return view;
        };

    public void mostrar_cartel (String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //Comprobar si el dispositivo tiene bluetooth
    public void comprobar_bluetooth() {

        // Phone does not support Bluetooth so let the user know and exit.
        if (bluetoothDispostivo == null) {
            /*new AlertDialog.Builder(getActivity())
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();*/


            /*new AlertDialog.Builder(getActivity())
                    // Set Dialog Icon
                    .setIcon(R.mipmap.ic_launcher)
                    // Set Dialog Title
                    .setTitle("Titulo hola")
                    // Set Dialog Message
                    .setMessage("mensaje hola")

                    // Positive button
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something else
                            Log.d("Dialogo", "Aceptar");
                        }
                    })

                    // Negative Button
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something else
                            Log.d("Dialogo", "Cancelar");
                        }
                    }).show();*/
           /* DialogoAceptarCancelar dialogo = new DialogoAceptarCancelar();
            dialogo.setTitulo("Titulo Dialogo");
            dialogo.setMensaje("Mensaje escrito");
            dialogo.getShowsDialog();*/
            new AlertDialog.Builder(getActivity())
                    // Set Dialog Icon
                    .setIcon(R.mipmap.ic_bluetooth_grey600_48dp)
                    // Set Dialog Title
                    .setTitle("Titulo")
                    // Set Dialog Message
                    .setMessage("Mensaje")

                    // Positive button
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something else
                            Log.d("Dialogo", "Aceptar");
                        }
                    })

                    // Negative Button
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something else
                            Log.d("Dialogo", "Cancelar");
                        }
                    }).show();
        }
    }

    public void habilitar_bluetooth() {
        if (!bluetoothDispostivo.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }

};