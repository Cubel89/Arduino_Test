package es.ateneasystems.arduinotest.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
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


    //Variables
    private View view;

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



        //Pulsaciones de botones
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_cartel("Botón no funcional");
                //createSimpleDialog("Permisos Bluetooth","Es necesario activar el bluetooth para poder buscar otros dispositovos");
                //showDialog();
            }
        });

        //Devolvemos la vista
        return view;
        };

    public void mostrar_cartel (String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showDialog() {
        Log.d(logname, "Dialog");
        DialogoAceptarCancelar dialogo = new DialogoAceptarCancelar();
        dialogo.setTitulo("Titulo Dialogo");
        dialogo.setMensaje("Mensaje escrito");
        dialogo.getShowsDialog();
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        // Do stuff here.
    }

};