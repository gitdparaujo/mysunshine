package com.example.master.mysunshine;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.master.mysunshine.data.WeatherContract;

/**
 * Created by Master on 22/07/2015.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public String mForecastDetail;
    private Uri mDetailUri;
    private ShareActionProvider mShareActionProvider;
    private DetailViewHolder mDetailViewHolder;

    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    private final int DETAIL_LOADER = 0;
    static final String DETAIL_URI = "URI";

    private static final String[] FORECAST_COLUMNS = {

            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.LocationEntry.COLUMN_COORD_LAT,
            WeatherContract.LocationEntry.COLUMN_COORD_LONG,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_DEGREES
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
    static final int COL_HUMIDITY = 9;
    static final int COL_PRESSURE = 10;
    static final int COL_WIND_SPEED = 11;
    static final int COL_DEGREES = 12;

    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if(mForecastDetail != null)
        {
            mShareActionProvider.setShareIntent(getShareIntent());
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent launchSettings = new Intent(getActivity(),SettingsActivity.class);
            startActivity(launchSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args != null)
        {
            mDetailUri = args.getParcelable(DETAIL_URI);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mDetailViewHolder = new DetailViewHolder(view);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mDetailUri == null)
        {
            return null;
        }
        return new CursorLoader(getActivity(), mDetailUri, FORECAST_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst())
        {
            Context context = getActivity();
            boolean isMetric = Utility.isMetric(context);

            String dayName = Utility.getDayName(context, data.getLong(COL_WEATHER_DATE));
            mDetailViewHolder.dayName.setText(dayName);
            String monthday = Utility.getFormattedMonthDay(context, data.getLong(COL_WEATHER_DATE));
            mDetailViewHolder.monthDay.setText(monthday);
            String maxTemp = Utility.formatTemperature(context, data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);
            mDetailViewHolder.maxTemp.setText(maxTemp);
            String minTemp = Utility.formatTemperature(context, data.getDouble(COL_WEATHER_MIN_TEMP), isMetric);
            mDetailViewHolder.minTemp.setText(minTemp);
            String forecastDesc = data.getString(COL_WEATHER_DESC);
            mDetailViewHolder.forecastDesc.setText(forecastDesc);
            double humidity = data.getDouble(COL_HUMIDITY);
            mDetailViewHolder.humidity.setText(getString(R.string.format_humidity, humidity));
            double pressure = data.getDouble(COL_PRESSURE);
            mDetailViewHolder.pressure.setText(getString(R.string.format_pressure, pressure));
            float windSpeed = data.getFloat(COL_WIND_SPEED);
            float degrees = data.getFloat(COL_DEGREES);
            mDetailViewHolder.wind.setText(Utility.getFormattedWind(context, windSpeed, degrees));
            int imageArt = Utility.getArtResourceForWeatherCondition(data.getInt(COL_WEATHER_CONDITION_ID));
            mDetailViewHolder.icon.setImageResource(imageArt);

            mForecastDetail = String.format("%s - %s - %s/%s",
                    new String[]{Utility.getFriendlyDayString(context,
                            data.getLong(COL_WEATHER_DATE)),
                            forecastDesc,
                            maxTemp,
                            minTemp});

            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(getShareIntent());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private Intent getShareIntent() {
        return new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                .putExtra(Intent.EXTRA_TEXT, mForecastDetail + " #MySunshineApp");
    }

    public class DetailViewHolder
    {
        public final TextView dayName;
        public final TextView monthDay;
        public final TextView maxTemp;
        public final TextView minTemp;
        public final TextView forecastDesc;
        public final TextView humidity;
        public final TextView wind;
        public final TextView pressure;
        public final ImageView icon;
        public DetailViewHolder(View view)
        {
            dayName = (TextView) view.findViewById(R.id.detail_day_name);
            monthDay = (TextView) view.findViewById(R.id.detail_month_day);
            maxTemp = (TextView) view.findViewById(R.id.detail_max_temp);
            minTemp = (TextView) view.findViewById(R.id.detail_min_temp);
            forecastDesc = (TextView) view.findViewById(R.id.detail_forecast_desc);
            humidity = (TextView) view.findViewById(R.id.detail_humidity);
            wind = (TextView) view.findViewById(R.id.detail_wind);
            pressure = (TextView) view.findViewById(R.id.detail_pressure);
            icon = (ImageView) view.findViewById(R.id.detail_icon);
        }
    }
    public void onLocationChanged(String newLocation)
    {
        Uri uri = mDetailUri;
        if (uri != null)
        {
            long date = WeatherContract.WeatherEntry.getDateFromUri(uri);
            Uri updateUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(newLocation, date);
            mDetailUri = updateUri;
            getLoaderManager().restartLoader(DETAIL_LOADER,null,this);
        }
    }
}
