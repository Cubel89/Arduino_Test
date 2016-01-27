package es.ateneasystems.arduinotest.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import es.ateneasystems.arduinotest.R;
import es.ateneasystems.arduinotest.global.Globales;


public class Terminal extends Fragment {
    /**
     * Log
     */
    private String logname = "Terminal (Fragment)";

    //Variables
    Globales globales;
    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");
    private View view;
    TextView tv_terminal;
    EditText txt_terminal;
    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    //private static String address = "00:15:FF:F2:19:5F";
    //private static String address = "98:D3:31:40:1F:C0";

    private static String address = "";

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
        txt_terminal = (EditText) view.findViewById(R.id.txt_terminal);
        tv_terminal = (TextView) view.findViewById(R.id.tv_terminal);
        globales = (Globales) getActivity().getApplicationContext();

        //Inicio
        fab_enviar.setVisibility(view.GONE);
        txt_terminal.setVisibility(view.GONE);
        //Log.d(logname,txt_terminal.getWidth()+" - "+btn_enviar.getWidth()+" = "+(txt_terminal.getWidth()-btn_enviar.getWidth()));
        //txt_terminal.setWidth(txt_terminal.getWidth()-btn_enviar.getWidth());
        //TODO: Meter la mac en la bariable de arriba
        Log.e(logname, globales.getArduino_conectado_mac());
        address = globales.getArduino_conectado_mac();


        //BLUETOOTH
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                    // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                    // create string from bytes array
                        sb.append(strIncom);                                                // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);                // extract string
                            sb.delete(0, sb.length());                                        // and clear
                            tv_terminal.setText("Data from Arduino: " + sbprint);            // update TextView
                        }
                        //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;
                }
            }

            ;
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();        // get Bluetooth adapter
        checkBTState();

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
                //mostrar_cartel("Sosiega que no hay nada que enviar!! :)");
                //fab_enviar.setVisibility(view.VISIBLE);
                //fab_escribir.setVisibility(view.GONE);
                //mConnectedThread.write("1");	// Send "1" via Bluetooth
                //address = globales.getArduino_a_conectar();
                Log.d(logname, txt_terminal.getText().toString());
                mConnectedThread.write(txt_terminal.getText().toString());    // Send "1" via Bluetooth

                //TODO: De momento mandaremos el numero y ya esta


            }
        });

        //Devolvemos la vista
        return view;
    };

    public void mostrar_cartel (String texto){
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * Metodos para el funcionamiento
     */
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(logname, "Could not create Insecure RFComm Connection", e);
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }


    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (btAdapter == null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(logname, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                /*Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);*/
            }
        }
    }

    private void errorExit(String title, String message) {
        Toast.makeText(view.getContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        //finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();        // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(logname, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
                appendLog(message);
            } catch (IOException e) {
                Log.d(logname, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(logname, "...onResume - try connect...");
        //Log.d(logname,globales.getArduino_a_conectar());
        address = globales.getArduino_conectado_mac();
        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(logname, "...Connecting...");
        automaticos("Conectando...");
        try {
            btSocket.connect();
            Log.d(logname, "....Connection ok...");
            automaticos("Conectado con "+globales.getArduino_conectado()+" [" + address+"]");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
                automaticos("Error al conectar!");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(logname, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(logname, "...In onPause()...");
        automaticos("Desconectado");

        try {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
            automaticos("Error al conectar!");
        }
    }


    /**
     * Funciones propias
     */
    void appendLog(String message) {

        StringBuilder encabezado = new StringBuilder();
        encabezado.append("[").append(timeformat.format(new Date())).append("]");
        SpannableString blackSpannable = new SpannableString(encabezado);
        blackSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, encabezado.length(), 0);
        tv_terminal.append(blackSpannable);


        tv_terminal.append(" ");


        StringBuilder mensaje = new StringBuilder();
        mensaje.append(message);
        SpannableString blueSpannable = new SpannableString(mensaje);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, mensaje.length(), 0);
        tv_terminal.append(blueSpannable);

        tv_terminal.append("\n");


        final int scrollAmount = tv_terminal.getLayout().getLineTop(tv_terminal.getLineCount()) - tv_terminal.getHeight();
        if (scrollAmount > 0)
            tv_terminal.scrollTo(0, scrollAmount);
        else tv_terminal.scrollTo(0, 0);

        txt_terminal.setText("");

    }

    void automaticos(String message) {


        StringBuilder automaticos = new StringBuilder();
        automaticos.append("[").append(timeformat.format(new Date())).append("]");
        SpannableString blackSpannable = new SpannableString(automaticos);
        blackSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, automaticos.length(), 0);
        tv_terminal.append(blackSpannable);


        tv_terminal.append(" ");


        StringBuilder mensaje = new StringBuilder();
        mensaje.append(message);
        SpannableString blueSpannable = new SpannableString(mensaje);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, mensaje.length(), 0);
        tv_terminal.append(blueSpannable);

        tv_terminal.append("\n");

    }


};