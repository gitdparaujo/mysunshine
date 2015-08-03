package com.example.master.mysunshine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.master.mysunshine.data.WeatherContract;
import com.example.master.mysunshine.service.SunshineService;

import java.util.Calendar;

public class ForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final String LOG_TAG = ForecastFragment.class.getSimpleName();
    private final int FORECAST_LOADER = 0;
    private final int CODE_ALARM = 0;
    private final String SELECTED_ITEM = "position";
    private int mPosition;
    private boolean mUseTodayLayout;

    private static final String[] FORECAST_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.LocationEntry.COLUMN_COORD_LAT,
            WeatherContract.LocationEntry.COLUMN_COORD_LONG
    };

    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DESC = 2;
    static final int COL_WEATHER_MAX_TEMP = 3;
    static final int COL_WEATHER_MIN_TEMP = 4;
    static final int COL_LOCATION_SETTING = 5;
    static final int COL_WEATHER_CONDITION_ID = 6;
    static final int COL_COORD_LAT = 7;
    static final int COL_COORD_LONG = 8;

    private ForecastAdapter mForecastAdapter;
    private ListView mForecastView;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        mForecastAdapter = new ForecastAdapter(getActivity(), null, 0);
        mForecastAdapter.setUseTodayLayout(mUseTodayLayout);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mForecastView = (ListView) rootView.findViewById(R.id.listview_forecast);

        mForecastView.setAdapter(mForecastAdapter);

        mForecastView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                if (cursor != null) {
                    long date = cursor.getLong(COL_WEATHER_DATE);
                    String locationSetting = Utility.getPreferredLocation(getActivity());
                    ((Callback) getActivity()).
                            onItemSelected(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(locationSetting, date));
                }
                mPosition = position;
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_ITEM))
        {
            mPosition = savedInstanceState.getInt(SELECTED_ITEM);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String locationSetting = Utility.getPreferredLocation(getActivity());
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());

        return new CursorLoader(getActivity(),
                weatherForLocationUri,
                FORECAST_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mForecastAdapter.swapCursor(data);
        if(mPosition != ListView.INVALID_POSITION)
        {
            mForecastView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mForecastAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition != ListView.INVALID_POSITION)
        {
            outState.putInt(SELECTED_ITEM,mPosition);
        }

        super.onSaveInstanceState(outState);
    }

    public void updateWeather(){
//        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(getActivity());
//        String location = Utility.getPreferredLocation(getActivity());
//        String[] parameters = new String[]{location};
//        fetchWeatherTask.execute(parameters);
        Intent alarmIntent = new Intent(getActivity(), SunshineService.AlarmReceiver.class)
                .putExtra(SunshineService.LOCATION_QUERY_EXTRA, Utility.getPreferredLocation(getActivity()));
//        getActivity().startService(intent);

        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(getActivity(),CODE_ALARM,alarmIntent,PendingIntent.FLAG_ONE_SHOT);

        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+5000,pi);
    }

    public void onLocationChanged()
    {
        updateWeather();
        getLoaderManager().restartLoader(FORECAST_LOADER,null,this);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    public void setUseTodayLayout(boolean useTodayLayout)
    {
        mUseTodayLayout = useTodayLayout;
        if(mForecastAdapter != null)
        {
            mForecastAdapter.setUseTodayLayout(mUseTodayLayout);
        }
    }
}

