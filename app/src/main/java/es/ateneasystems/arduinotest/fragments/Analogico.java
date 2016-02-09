package es.ateneasystems.arduinotest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.ateneasystems.arduinotest.R;
import es.ateneasystems.arduinotest.global.Globales;


public class Analogico extends Fragment {
    /**
     * Log
     */
    private String logname = "Analogico (Fragment)";
    //Variables
    private View view;
    Globales globales;


    public Analogico() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_analogico, container, false);


        //Devolvemos la vista
        return view;
    }


}
