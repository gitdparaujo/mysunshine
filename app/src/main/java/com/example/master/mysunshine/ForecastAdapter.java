package com.example.master.mysunshine;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {

    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;
    private boolean mUseTodayLayout;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    public void setUseTodayLayout(boolean useTodayLayout)
    {
        mUseTodayLayout = useTodayLayout;
    }

    public static class ViewHolder
    {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView maxTempView;
        public final TextView minTempView;
        public ViewHolder(View view)
        {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            maxTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            minTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }

    }


    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutID = -1;

        layoutID = (viewType == VIEW_TYPE_TODAY) ? R.layout.list_item_forecast_today : R.layout.list_item_forecast;

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        ViewHolder viewHolder = new ViewHolder(view);

        boolean isMetric = Utility.isMetric(context);

        viewHolder.dateView.
                setText(Utility.getFriendlyDayString(context, cursor.getLong(ForecastFragment.COL_WEATHER_DATE)));

        viewHolder.descriptionView.
                setText(cursor.getString(ForecastFragment.COL_WEATHER_DESC));

        viewHolder.maxTempView.
                setText(Utility.formatTemperature(context,cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP), isMetric));

        viewHolder.minTempView.
                setText(Utility.formatTemperature(context,cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP), isMetric));

        int weatherIcon;
        int viewType = getItemViewType(cursor.getPosition());

        if(viewType == VIEW_TYPE_TODAY)
        {
            weatherIcon = Utility.
                    getArtResourceForWeatherCondition(cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID));
        } else
        {
            weatherIcon = Utility.
                    getIconResourceForWeatherCondition(cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID));
        }

        viewHolder.iconView.setImageResource(weatherIcon);
    }
}