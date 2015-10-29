package de.interitus.michael.wetterwidget;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Toolbar toolbar;


    DrawerLayout drawerlayoutgesamt;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    /* Progressbutton start */
    Menu mymenu;
    MenuItem progress_menu_item;
    /* Progressbutton ende */

    private TextView name, zeit, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextView) findViewById(R.id.stationName);
        zeit = (TextView) findViewById(R.id.stationZeit);
        temp = (TextView) findViewById(R.id.stationTemp);

        /* Hintergrundprozess start */
        AlarmManager updateService = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);

        Intent startUpdateServiceIntent = new Intent(MainActivity.this, UpdateService.class);
        PendingIntent startUpdateServicePendingIntent = PendingIntent.getService(MainActivity.this,0,startUpdateServiceIntent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 1000*60*5);

        updateService.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 1000*60*5, startUpdateServicePendingIntent);
        /* Hintergrundprozess ende */


        TextView tvheaderdate = (TextView) findViewById(R.id.headerTextSmall);
        tvheaderdate.setText("Stand: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Das Wetter in Üfingen");

        if (Build.VERSION.SDK_INT >= 21){
            toolbar.setElevation(25);
        }


        drawerlayoutgesamt = (DrawerLayout) findViewById(R.id.drawerlayoutgesamt);
        drawerToggle = new ActionBarDrawerToggle(this,drawerlayoutgesamt,R.string.auf,R.string.zu);
        drawerlayoutgesamt.setDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.drawerItemView1:{
                        /* Was soll bei MenuItem 1 passieren */
                        break;
                    }

                    case R.id.drawerItemView2:{
                        /* Was soll bei MenuItem 2 passieren */
                        break;
                    }

                    case R.id.drawerItemView3:{
                        /* Was soll bei MenuItem 3 passieren */
                        break;
                    }
                }

                drawerlayoutgesamt.closeDrawers();
                menuItem.setChecked(true);

                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mymenu = menu;
        progress_menu_item = mymenu.findItem(R.id.action_progress_show);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_progress_show) {

            progress_menu_item.setActionView(R.layout.menu_item_layout);

            /* was soll beim drücken vom RefreshButton passieren
             *
             * Icon wieder auf standart setzen:
             * progress_menu_item.setActionView(null);
             *
             * Anderes Icon anzeigen:
             * progress_menu_item_setIcon(R.drawable.???);
              *
              * */

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final StationData weather = StationUtils.getWeather();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                name.setText(weather.name);
                                zeit.setText(weather.zeit);
                                temp.setText(weather.currentTemp);
                            }
                        });

                        /* IOException, MalformedURLException, JSONException */

                    } catch (Exception e) {
                        Log.e(TAG, "getWeather()", e);
                    }
                }
            }).start();
            progress_menu_item.setActionView(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(new Configuration());
    }


}
