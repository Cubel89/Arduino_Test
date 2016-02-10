package es.ateneasystems.arduinotest.fragments;


import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageButton;

import es.ateneasystems.arduinotest.MainActivity;
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
        ImageButton btn_descargar = (ImageButton) view.findViewById(R.id.btn_descargar);
        ImageButton btn_compartir = (ImageButton) view.findViewById(R.id.btn_compartir);


        //Si el dispositivo no tiene bluetooth mostramos un mensaje
        if (bluetoothDispostivo == null) {
            mostrar_cartel(getActivity().getString(R.string.bluetooth), getActivity().getString(R.string.alerta_no_bluetooth), R.mipmap.ic_alert_grey600_48dp, false);
        }



        //Pulsaciones de botones
        btn_descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apis.ateneasystems.es/arduino_test/arduino_test.zip"));
                startActivity(browserIntent);


            }
        });
        btn_compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_cartel("Boton no funcional");
            }
        });

        //Devolvemos la vista
        return view;
        };

    public void mostrar_cartel (String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //Dialogos
    public void mostrar_cartel(String titulo, String mensaje, int identificador_imagen, boolean pregunta) {
        //boolean respuesta = false;

        //Creamos el cartel
        AlertDialog.Builder cartel_mostrar = new AlertDialog.Builder(getActivity());

        //Añadimos su icono
        cartel_mostrar.setIcon(identificador_imagen);

        //Añadimos titulo
        cartel_mostrar.setTitle(titulo);

        //Añadimos mensaje
        cartel_mostrar.setMessage(mensaje);

        //Comprobamos si es informacion o pregunta
        if (pregunta) { //Si es verdadero añadiremos los dos botones
            cartel_mostrar.setPositiveButton(getActivity().getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    Log.d("Dialogo", getActivity().getString(R.string.boton_aceptar));
                    //return true;
                }
            });
            cartel_mostrar.setNegativeButton(getActivity().getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    Log.d("Dialogo", getActivity().getString(R.string.boton_cancelar));
                    //return false;
                }
            });
        } else { //Si es falso, solo uno
            cartel_mostrar.setPositiveButton(getActivity().getString(R.string.boton_leido), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    Log.d("Dialogo", getActivity().getString(R.string.boton_leido));
                    //return true;
                }
            });
        }

        //Mostramos el cartel
        cartel_mostrar.show();
    }

};