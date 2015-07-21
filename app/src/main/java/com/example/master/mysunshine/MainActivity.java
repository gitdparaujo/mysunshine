package com.example.master.mysunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private final String FORECASTFRAGMENT_TAG = "main_fragment";

    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment(),FORECASTFRAGMENT_TAG)
                    .commit();
        }
        mLocation = Utility.getPreferredLocation(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent launchSettings = new Intent(this,SettingsActivity.class);
            startActivity(launchSettings);
            return true;
        }

        if (id == R.id.map_view) {
            displayLocation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void displayLocation(){
        String location = Utility.getPreferredLocation(this);
        Uri.Builder builder = new Uri.Builder();
        Uri mapUri = builder.scheme("geo")
                .authority("0,0")
                .appendQueryParameter("q",location)
                .build();
        Intent launcher = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(mapUri);
        if(launcher.resolveActivity(getPackageManager()) != null)
        {
            startActivity(launcher);
        }
        else
        {
            Log.e(LOG_TAG,"There is no app capable of handling this event");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onResume() {
        String location = Utility.getPreferredLocation(this);
        if(location != null && !location.equals(mLocation))
        {
            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().
                    findFragmentByTag(FORECASTFRAGMENT_TAG);
            if (ff != null)
            {
                ff.onLocationChanged();
            }
            mLocation = location;
        }
        super.onResume();
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG,"onStart");
    }
}
