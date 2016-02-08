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

public class Digital extends Fragment {
    //Variables
    private View view;
    Globales globales;

    public Digital() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_digital, container, false);


        //Devolvemos la vista
        return view;
    }
}
