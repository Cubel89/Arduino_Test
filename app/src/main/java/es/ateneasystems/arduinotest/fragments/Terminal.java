package es.ateneasystems.arduinotest.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import es.ateneasystems.arduinotest.R;


public class Terminal extends Fragment {
    /**
     * Log
     */
    private String logname = "Terminal (Fragment)";

    //Variables
    private View view;

    public Terminal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_terminal, container, false);

        //Declaracion de componentes
        final FloatingActionButton fab_enviar = (FloatingActionButton) view.findViewById(R.id.fab_enviar);
        final FloatingActionButton fab_escribir = (FloatingActionButton) view.findViewById(R.id.fab_escribir);
        final EditText txt_terminal = (EditText) view.findViewById(R.id.txt_terminal);

        //Inicio
        fab_enviar.setVisibility(view.GONE);
        txt_terminal.setVisibility(view.GONE);
        //Log.d(logname,txt_terminal.getWidth()+" - "+btn_enviar.getWidth()+" = "+(txt_terminal.getWidth()-btn_enviar.getWidth()));
        //txt_terminal.setWidth(txt_terminal.getWidth()-btn_enviar.getWidth());


        //Pulsaciones de botones
        fab_escribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrar_cartel("Botón no funcional");
                fab_enviar.setVisibility(view.VISIBLE);
                txt_terminal.setVisibility(view.VISIBLE);
                fab_escribir.setVisibility(view.GONE);

            }
        });

        fab_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_cartel("Sosiega que no hay nada que enviar!! :)");
                //fab_enviar.setVisibility(view.VISIBLE);
                //fab_escribir.setVisibility(view.GONE);

            }
        });

        //Devolvemos la vista
        return view;
    };

    public void mostrar_cartel (String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

};