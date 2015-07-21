/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.master.mysunshine;

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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.master.mysunshine.data.WeatherContract;


public class DetailActivity extends ActionBarActivity {

    private String forecastDetail;
    private Uri detailUri;

    private ShareActionProvider shareActionProvider;

    private final String LOG_TAG = ForecastFragment.class.getSimpleName();
    private final int DETAIL_LOADER = 0;

    private static final String[] FORECAST_COLUMNS = {

            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
    };

    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DESC = 2;
    static final int COL_WEATHER_MAX_TEMP = 3;
    static final int COL_WEATHER_MIN_TEMP = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Intent intent = getIntent();
        if(intent != null)
        {
            detailUri = intent.getData();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareItem);

        if(forecastDetail != null)
        {
            shareActionProvider.setShareIntent(getShareIntent());
        }

        return true;
    }

    private Intent getShareIntent()
    {
        return new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                .putExtra(Intent.EXTRA_TEXT,forecastDetail + " #MySunshineApp");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent launchSettings = new Intent(this,SettingsActivity.class);
            startActivity(launchSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            getLoaderManager().initLoader(DETAIL_LOADER,null,this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_detail, container, false);
      }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(),detailUri,FORECAST_COLUMNS,null,null,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            try
            {
                data.moveToFirst();
                String date = Utility.formatDate(data.getLong(COL_WEATHER_DATE));
                String description = data.getString(COL_WEATHER_DESC);
                String maxTemp = Utility.formatTemperature(
                        data.getDouble(COL_WEATHER_MAX_TEMP),
                        Utility.isMetric(getActivity()));
                String minTemp = Utility.formatTemperature(
                        data.getDouble(COL_WEATHER_MIN_TEMP),
                        Utility.isMetric(getActivity()));

                forecastDetail = String.format("%s - %s - %s/%s",new String[]{date,description,maxTemp,minTemp});

                TextView textView = (TextView) getActivity().findViewById(R.id.forecast_text);
                textView.setText(forecastDetail);

                if (shareActionProvider != null)
                {
                    shareActionProvider.setShareIntent(getShareIntent());
                }
            }finally {
                data.close();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }
    }
}