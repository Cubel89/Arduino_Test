package es.ateneasystems.arduinotest;

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

import es.ateneasystems.arduinotest.fragments.Arduino;
import es.ateneasystems.arduinotest.fragments.Home;
import es.ateneasystems.arduinotest.fragments.Terminal;

public class MainActivity extends AppCompatActivity {
    /**
     * Log
     */
    private String logname = "MainActivity";

    //Variables
    private InterstitialAd mInterstitialAd;
    private long startTime = 0L; //Donde se guardara la informacion de cuando inicio la app
    private Handler customHandler = new Handler();


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

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }


        //Inicio TODO Esto aun no funciona
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(contador_tiempo, 0);


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
            fragment = new Terminal();
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
        //Cargamos la publicidad
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private Runnable contador_tiempo = new Runnable() {
        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            if (mins > 5) {
                Log.d(logname, String.valueOf(mins));
                showInterstitial();
                // mostrar publicidad y poner el sistema a 0
                startTime = SystemClock.uptimeMillis();
            }
            Log.d(logname, String.valueOf(mins));
        }
    };
}
