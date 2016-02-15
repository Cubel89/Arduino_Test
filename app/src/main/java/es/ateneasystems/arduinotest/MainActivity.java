package es.ateneasystems.arduinotest;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;

import es.ateneasystems.arduinotest.fragments.Analogico;
import es.ateneasystems.arduinotest.fragments.Arduino;
import es.ateneasystems.arduinotest.fragments.Digital;
import es.ateneasystems.arduinotest.fragments.Home;
import es.ateneasystems.arduinotest.fragments.Terminal;
import es.ateneasystems.arduinotest.global.Globales;

public class MainActivity extends AppCompatActivity {
    /**
     * Log
     */
    private String logname = "MainActivity";

    //Variables
    private Globales globales;
    private InterstitialAd mInterstitialAd;
    private View view;


    /**
     * Instancia del drawer
     */
    private DrawerLayout drawerLayout;

    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Variables globales
        globales = (Globales) getApplication();
        view = findViewById(android.R.id.content);
        ;

        setToolbar(); // Setear Toolbar como action bar


        //Si el dispositivo no tiene bluetooth mostramos un mensaje
        globales.setbluetoothDispositivoAdapter(globales.getbluetoothDispositivoAdapter().getDefaultAdapter());
        if (globales.getbluetoothDispositivoAdapter() == null) {
            mostrar_cartel(getString(R.string.bluetooth), getString(R.string.alerta_no_bluetooth), R.mipmap.ic_alert_grey600_48dp, false);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }


        //Inicio
        //mostrar aviso de cookies
        if (!globales.getAviso_cookies()) {
            aviso_cookies();
        }


        //Publicidad
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.publicidad_Intersticial_id));


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d(logname, "Cerrada publicidad");
            }
        });





    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (globales.getPublicidad()) {
            showInterstitial();
        }

        Log.d(logname, "RESUME");
    }

    private void selectItem(String title) {
        Log.d(logname,title);
        //Cargamos el fragment
        cargar_fragment(title);

        drawerLayout.closeDrawers(); // Cerrar drawer
        setTitle(title); // Setear título actual*/

    }

    public void cargar_fragment(String title){
        //Creamos la variable fragment
        Fragment fragment = null;


        //Comprobamos el fragment
        /*switch (title){

            case home:
                fragment = new Home();
                break;
            case arduino:
                fragment = new Arduino();
                break;

            default:
                fragment = new Home();
                        Snackbar.make(findViewById(android.R.id.content), "Activity no existente", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    break;

        }*/

        //Comprobamos el fragment
        if (title == getApplication().getString(R.string.home_item)) fragment = new Home();
        else if (title == getApplication().getString(R.string.arduino_item))
            fragment = new Arduino();
        else if (title == getApplication().getString(R.string.terminal_item))
            if (globales.getBtSocket() != null) fragment = new Terminal();

        else if (title == getApplication().getString(R.string.log_out_item))
            System.exit(0);
        else if (title == getApplication().getString(R.string.digital_item))
                if (globales.getBtSocket() != null) fragment = new Digital();
        else if (title == getApplication().getString(R.string.analogico_item))
                    if (globales.getBtSocket() != null) fragment = new Analogico();
        else {
            fragment = new Home();
            Snackbar.make(findViewById(android.R.id.content), "Activity no existente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        //Cargamos el fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    private void showInterstitial() {
        Log.d(logname, "DEBERIA SALIR PUBLICIDAD");

        Log.d(logname, "PUBLICIDAD");
            //Cargamos la publicidad
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            // Show the ad if it's ready. Otherwise toast and restart the game.
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }


    }

    private void aviso_cookies() {
        boolean pregunta = true;

        //Creamos el cartel
        AlertDialog.Builder cartel_mostrar = new AlertDialog.Builder(this);

        //Añadimos su icono
        cartel_mostrar.setIcon(R.mipmap.ic_information_outline_grey600_48dp);

        //Añadimos titulo
        cartel_mostrar.setTitle(this.getString(R.string.titulo_politica_cookies));

        //Añadimos mensaje
        cartel_mostrar.setMessage(this.getString(R.string.texto_politica_cookies));

        //Comprobamos si es informacion o pregunta
        if (pregunta) { //Si es verdadero añadiremos los dos botones
            cartel_mostrar.setPositiveButton(this.getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    globales.setAviso_cookies(true);
                }
            });
            cartel_mostrar.setNegativeButton(this.getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
        } else { //Si es falso, solo uno
            cartel_mostrar.setPositiveButton(this.getString(R.string.boton_leido), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    //Log.d("Dialogo", getActivity().getString(R.string.boton_leido));
                    //solicitar_permisos();
                    //return true;
                }
            });
        }

        //Mostramos el cartel
        cartel_mostrar.show();
    }


    @Override
    public void onPause() {
        super.onPause();

        Log.d(logname, "PAUSE");
    }

    //Dialogos
    public void mostrar_cartel(String titulo, String mensaje, int identificador_imagen, boolean pregunta) {
        //boolean respuesta = false;

        //Creamos el cartel
        AlertDialog.Builder cartel_mostrar = new AlertDialog.Builder(this);

        //Añadimos su icono
        cartel_mostrar.setIcon(identificador_imagen);

        //Añadimos titulo
        cartel_mostrar.setTitle(titulo);

        //Añadimos mensaje
        cartel_mostrar.setMessage(mensaje);

        //Comprobamos si es informacion o pregunta
        if (pregunta) { //Si es verdadero añadiremos los dos botones
            cartel_mostrar.setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    Log.d("Dialogo", getString(R.string.boton_aceptar));
                    //return true;
                }
            });
            cartel_mostrar.setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    Log.d("Dialogo", getString(R.string.boton_cancelar));
                    //return false;
                }
            });
        } else { //Si es falso, solo uno
            cartel_mostrar.setPositiveButton(getString(R.string.boton_leido), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    Log.d("Dialogo", getString(R.string.boton_leido));
                    //return true;
                }
            });
        }

        //Mostramos el cartel
        cartel_mostrar.show();
    }

}
