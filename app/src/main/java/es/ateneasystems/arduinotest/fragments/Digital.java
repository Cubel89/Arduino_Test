package es.ateneasystems.arduinotest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import es.ateneasystems.arduinotest.R;
import es.ateneasystems.arduinotest.global.Globales;

public class Digital extends Fragment {
    /**
     * Log
     */
    private String logname = "Digital (Fragment)";
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

        //Declaracion de componentes
        Button btn_low_p02 = (Button) view.findViewById(R.id.btn_low_p02);
        Button btn_high_p02 = (Button) view.findViewById(R.id.btn_high_p02);
        Button btn_low_p03 = (Button) view.findViewById(R.id.btn_low_p03);
        Button btn_high_p03 = (Button) view.findViewById(R.id.btn_high_p03);
        Button btn_low_p04 = (Button) view.findViewById(R.id.btn_low_p04);
        Button btn_high_p04 = (Button) view.findViewById(R.id.btn_high_p04);
        Button btn_low_p05 = (Button) view.findViewById(R.id.btn_low_p05);
        Button btn_high_p05 = (Button) view.findViewById(R.id.btn_high_p05);
        Button btn_low_p06 = (Button) view.findViewById(R.id.btn_low_p06);
        Button btn_high_p06 = (Button) view.findViewById(R.id.btn_high_p06);
        Button btn_low_p07 = (Button) view.findViewById(R.id.btn_low_p07);
        Button btn_high_p07 = (Button) view.findViewById(R.id.btn_high_p07);
        Button btn_low_p08 = (Button) view.findViewById(R.id.btn_low_p08);
        Button btn_high_p08 = (Button) view.findViewById(R.id.btn_high_p08);
        Button btn_low_p09 = (Button) view.findViewById(R.id.btn_low_p09);
        Button btn_high_p09 = (Button) view.findViewById(R.id.btn_high_p09);
        Button btn_low_p10 = (Button) view.findViewById(R.id.btn_low_p10);
        Button btn_high_p10 = (Button) view.findViewById(R.id.btn_high_p10);
        Button btn_low_p11 = (Button) view.findViewById(R.id.btn_low_p11);
        Button btn_high_p11 = (Button) view.findViewById(R.id.btn_high_p11);
        Button btn_low_p12 = (Button) view.findViewById(R.id.btn_low_p12);
        Button btn_high_p12 = (Button) view.findViewById(R.id.btn_high_p12);
        Button btn_low_p13 = (Button) view.findViewById(R.id.btn_low_p13);
        Button btn_high_p13 = (Button) view.findViewById(R.id.btn_high_p13);

        Button btn_low_pa0 = (Button) view.findViewById(R.id.btn_low_pa0);
        Button btn_high_pa0 = (Button) view.findViewById(R.id.btn_high_pa0);
        Button btn_low_pa1 = (Button) view.findViewById(R.id.btn_low_pa1);
        Button btn_high_pa1 = (Button) view.findViewById(R.id.btn_high_pa1);
        Button btn_low_pa2 = (Button) view.findViewById(R.id.btn_low_pa2);
        Button btn_high_pa2 = (Button) view.findViewById(R.id.btn_high_pa2);
        Button btn_low_pa3 = (Button) view.findViewById(R.id.btn_low_pa3);
        Button btn_high_pa3 = (Button) view.findViewById(R.id.btn_high_pa3);
        Button btn_low_pa4 = (Button) view.findViewById(R.id.btn_low_pa4);
        Button btn_high_pa4 = (Button) view.findViewById(R.id.btn_high_pa4);
        Button btn_low_pa5 = (Button) view.findViewById(R.id.btn_low_pa5);
        Button btn_high_pa5 = (Button) view.findViewById(R.id.btn_high_pa5);


        //Pulsaciones de boton
        btn_high_p02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p02");
            }
        });
        btn_low_p02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p02");
            }
        });

        btn_high_p03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p03");
            }
        });
        btn_low_p03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p03");
            }
        });

        btn_high_p04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p04");
            }
        });
        btn_low_p04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p04");
            }
        });

        btn_high_p05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p05");
            }
        });
        btn_low_p05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p05");
            }
        });

        btn_high_p06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p06");
            }
        });
        btn_low_p06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p06");
            }
        });

        btn_high_p07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p07");
            }
        });
        btn_low_p07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p07");
            }
        });

        btn_high_p08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p08");
            }
        });
        btn_low_p08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p08");
            }
        });

        btn_high_p09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p09");
            }
        });
        btn_low_p09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p09");
            }
        });

        btn_high_p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p10");
            }
        });
        btn_low_p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p10");
            }
        });

        btn_high_p11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p11");
            }
        });
        btn_low_p11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p11");
            }
        });

        btn_high_p12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p12");
            }
        });
        btn_low_p12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p12");
            }
        });

        btn_high_p13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_p13");
            }
        });
        btn_low_p13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_p13");
            }
        });

        btn_high_pa0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_pa0");
            }
        });
        btn_low_pa0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_pa0");
            }
        });

        btn_high_pa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_pa1");
            }
        });
        btn_low_pa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_pa1");
            }
        });

        btn_high_pa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_pa2");
            }
        });
        btn_low_pa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_pa2");
            }
        });

        btn_high_pa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_pa3");
            }
        });
        btn_low_pa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_pa3");
            }
        });

        btn_high_pa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_pa4");
            }
        });
        btn_low_pa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_pa4");
            }
        });

        btn_high_pa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high("btn_high_pa5");
            }
        });
        btn_low_pa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low("btn_low_pa5");
            }
        });

        //Devolvemos la vista
        return view;
    }

    private void high(String boton) {
        String pin;
        pin = boton.substring(10);

        Log.d(logname, boton);
        Log.d(logname, pin);
        Log.d(logname, pin + "|1");

    }

    private void low(String boton) {
        String pin;
        pin = boton.substring(9);

        Log.d(logname, boton);
        Log.d(logname, pin);
        Log.d(logname, pin + "|0");

    }

}
