package org.brunokam.personalcoach;

import android.app.Activity;
import android.util.Log;

import org.apache.http.impl.cookie.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Extras {

    private static String LOG_TAG = "Extras";

    private static Activity mActivity;

    public static void initialise(Activity activity) {
        mActivity = activity;
    }

    public static String formatDate(Date date) {
        long daySeconds = 24 * 3600;
        long weekSeconds = 7 * 24 * 3600;

        long currentTimestamp = System.currentTimeMillis() / 1000L;
        long dateTimestamp = date.getTime() / 1000L;
        long deltaRounded = roundTimestampToDay(dateTimestamp) - roundTimestampToDay(currentTimestamp);

        String dateFormat;

        if (roundTimestampToDay(dateTimestamp) >= roundTimestampToDay(currentTimestamp)) {
            if (deltaRounded == 0) {
                dateFormat = mActivity.getResources().getString(R.string.date_format_today);
            } else if (deltaRounded == daySeconds) {
                dateFormat = mActivity.getResources().getString(R.string.date_format_tomorrow);
            } else if (deltaRounded <= weekSeconds) {
                dateFormat = mActivity.getResources().getString(R.string.date_format_week);
            } else {
                dateFormat = mActivity.getResources().getString(R.string.date_format_long);
            }
        } else {
            dateFormat = mActivity.getResources().getString(R.string.date_format_long);
        }

        return new SimpleDateFormat(dateFormat, Locale.getDefault()).format(date);
    }

    public static String formatTimestamp(long timestamp) {
        return formatDate(new Date(timestamp * 1000L));
    }

    public static String formatTimestamp(int timestamp) {
        return formatTimestamp((long) timestamp);
    }

    public static long roundTimestampToDay(long timestamp) {
        Date date = new Date(timestamp * 1000L);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() / 1000L;
    }

    public static int roundTimestampToDay(int timestamp) {
        return (int) roundTimestampToDay((long) timestamp);
    }

}
