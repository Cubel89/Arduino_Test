package es.ateneasystems.arduinotest.fragments;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

import es.ateneasystems.arduinotest.R;
import es.ateneasystems.arduinotest.global.Globales;

public class Arduino extends Fragment {
    /**
     * Log
     */
    private String logname = "Arduino (Fragment)";
    //Bluetooth
    /**
     * Member fields
     */
    private BluetoothAdapter mBtAdapter;

    /**
     * Newly discovered devices
     */
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    //Variables estaticas
    public static int REQUEST_BLUETOOTH = 1;

    //Variables
    private View view;
    BluetoothAdapter bluetoothDispostivo; //TODO: Creo que esto sobra
    Globales globales;

    public Arduino() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_arduino, container, false);

        //Declaracion de componentes
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        bluetoothDispostivo = BluetoothAdapter.getDefaultAdapter();
        final TextView txt_emparejados = (TextView) view.findViewById(R.id.txt_emparejados);
        final TextView txt_nuevos = (TextView) view.findViewById(R.id.txt_nuevos);
        globales = (Globales) getActivity().getApplicationContext();


        //Si el dispositivo no tiene bluetooth mostramos un mensaje
        if (bluetoothDispostivo == null) {
            mostrar_cartel(getActivity().getString(R.string.bluetooth), getActivity().getString(R.string.alerta_no_bluetooth), R.mipmap.ic_alert_grey600_48dp, false);
        }


        //BLUETOOTH
        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        ArrayAdapter<String> pairedDevicesArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.device_name);

        // Find and set up the ListView for paired devices
        final ListView pairedListView = (ListView) view.findViewById(R.id.paired_devices);
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        final ListView newDevicesListView = (ListView) view.findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            view.findViewById(R.id.txt_emparejados).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "No hay dispositivos";
            pairedDevicesArrayAdapter.add(noDevices);
        }


        //Inicio
        txt_emparejados.setVisibility(view.GONE);
        txt_nuevos.setVisibility(view.GONE);
        pairedListView.setVisibility(view.GONE);
        newDevicesListView.setVisibility(view.GONE);


        //Pulsaciones de botones
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_cartel("Comprobando bluetooth...");
                comprobar_bluetooth();
                txt_emparejados.setVisibility(view.VISIBLE);
                txt_nuevos.setVisibility(view.VISIBLE);
                pairedListView.setVisibility(view.VISIBLE);
                newDevicesListView.setVisibility(view.VISIBLE);
                doDiscovery();


            }
        });

        //Devolvemos la vista
        return view;
    }

    ;

    public void mostrar_cartel(String texto) {
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //Comprobar si el dispositivo tiene bluetooth
    public void comprobar_bluetooth() {

        // Phone does not support Bluetooth so let the user know and exit.
        if (bluetoothDispostivo == null) {
            mostrar_cartel(getActivity().getString(R.string.bluetooth), getActivity().getString(R.string.dispositivo_no_bluetooth), R.mipmap.ic_alert_grey600_48dp, false);

        } else {
            //Si dispone de bluetooth
            habilitar_bluetooth();
        }
    }

    public void solicitar_permisos(){
        if (!bluetoothDispostivo.isEnabled()) {
            //Mandamos la orden para activar el bluetooth, esto ya muestra el cartel de permisos para activar
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }

    public void habilitar_bluetooth() {
        if (!bluetoothDispostivo.isEnabled()) {
            //Mostramos informacion sobre lo que vamos a pedir ahora
            mostrar_cartel_activar_bluetooth(getActivity().getString(R.string.permisos_bluetooth), getActivity().getString(R.string.info_activar_bluetooth), R.mipmap.ic_bluetooth_grey600_48dp, false);

        }
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
    public void mostrar_cartel_activar_bluetooth(String titulo, String mensaje, int identificador_imagen, boolean pregunta) {
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
                    solicitar_permisos();
                    //return true;
                }
            });
        }

        //Mostramos el cartel
        cartel_mostrar.show();
    }


    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        //Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        //setProgressBarIndeterminateVisibility(true);
        //setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        view.findViewById(R.id.txt_nuevos).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            // Cancel discovery because it's costly and we're about to connect
            mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            String nombre_dispostivo = info.substring(0,info.length() - 17);

            Log.d(logname, address);
            //Guardamos la mac del dispositivo
            globales.setArduino_conectado_mac(address);

            //Guardamos el nombre del dispositivo
            globales.setArduino_conectado(nombre_dispostivo);

            //Cargamos el fragment
            dispostivo_conectado();


            // Create the result Intent and include the MAC address
           /* Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            finish();
            */

        }
    };

    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
                //setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = "No se han encontrado más dispositivos";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

    private void dispostivo_conectado() {
        //Creamos la variable fragment
        Fragment fragment = null;

        fragment = new Terminal();
        //Cargamos el fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();

        //TODO: falta cambiar el titulo i el boton del menu seleccionado
    }

};