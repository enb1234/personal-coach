package org.brunokam.personalcoach;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static Activity mActivity;

    public static void initialise(Activity activity) {
        mActivity = activity;
    }

    public static String formatDate(Date date) {
        long weekSeconds = 7 * 24 * 3600;

        long currentTimestamp = System.currentTimeMillis() / 1000L;
        long dateTimestamp = date.getTime() / 1000L;
        long delta = dateTimestamp - currentTimestamp;

        String dateFormat;

        if (dateTimestamp > currentTimestamp) {
            if (delta <= weekSeconds) {
                dateFormat = mActivity.getResources().getString(R.string.date_format_week);
            } else {
                dateFormat = mActivity.getResources().getString(R.string.date_format_long);
            }
        } else {
            dateFormat = mActivity.getResources().getString(R.string.date_format_long);
        }

        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String formatTimestamp(long timestamp) {
        return Utils.formatDate(new Date(timestamp * 1000L));
    }

    public static String formatTimestamp(int timestamp) {
        return Utils.formatDate(new Date((long) timestamp * 1000L));
    }
}
