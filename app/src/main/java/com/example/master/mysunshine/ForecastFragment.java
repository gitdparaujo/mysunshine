package com.example.master.mysunshine;

/**
 * Created by Master on 20/06/2015.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private final String LOG_TAG = ForecastFragment.class.getSimpleName();

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> forecastData = new ArrayList<String>();
        forecastData.add("Today - Sunny - 88/63");
        forecastData.add("Tomorrow - Foggy - 70/46");
        forecastData.add("Weds - Cloudy - 72/63");
        forecastData.add("Thrus - Rainy - 64/51");
        forecastData.add("Fri - Foggy - 70/46");
        forecastData.add("Sat - Sunny - 76/68");

        ArrayAdapter listAdapter = new ArrayAdapter(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecastData);

        ListView forecastView = (ListView) rootView.findViewById(R.id.listview_forecast);
        forecastView.setAdapter(listAdapter);

        // Construct the URL for the OpenWeatherMap query
        // Possible parameters are avaiable at OWM's forecast API page, at
        // http://openweathermap.org/API#forecast

        URL url = null;

        try{
            url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
            FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
            fetchWeatherTask.execute(url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Malformed URL", e);
        }
        return rootView;
    }

    private class FetchWeatherTask extends AsyncTask<URL, Void, String>
    {
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected String doInBackground(URL... urls) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = urls[0];

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return forecastJsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(LOG_TAG, "Got " + result.length() + " characters as response!");
        }
    }
}

