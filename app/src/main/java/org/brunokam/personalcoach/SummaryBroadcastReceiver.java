package org.brunokam.personalcoach;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SummaryBroadcastReceiver extends BroadcastReceiver {

    private static String LOG_TAG = "SummaryBroadcastReceiver";

    private static int NID = 1;
    private static MainActivity mActivity;

    public static void reinitialise() {
        Calendar dailySummaryTime = User.getInstance().getDailySummaryTime();

        Date now = new Date();
        Date summaryTime = dailySummaryTime.getTime();

        // Postpones notification to the next day because current day's summary time has gone
        if (summaryTime.before(now)) {
            dailySummaryTime.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(mActivity, SummaryBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mActivity.getApplicationContext().getSystemService(MainActivity.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dailySummaryTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.i(LOG_TAG, "initialised");
    }

    public static void initialise(MainActivity mainActivity) {
        mActivity = mainActivity;
        reinitialise();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<Resolution> resolutionList = ResolutionDatabase.getInstance(context).all();
        for (Resolution resolution: resolutionList) {
            if (resolution.isSummaryTimeToday()) {
                this.notify(context, resolution.getID(), resolution.getTitle());
            }
        }
    }

    private void notify(Context context, Integer resolutionID, String contentTitle) {
        Intent intent = new Intent(context, AddSummaryActivity.class);
        intent.putExtra("resolution_id", resolutionID);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context.getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(contentTitle)
                .setContentText(context.getResources().getString(R.string.notification_summary_text))
                .setContentIntent(pendingIntent)
                .build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NID++, notification);
    }

}
