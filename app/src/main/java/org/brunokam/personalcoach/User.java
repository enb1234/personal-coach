package org.brunokam.personalcoach;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class User {

    private static final String LOG_TAG = "User";

    private static final String PREFS_FILE_NAME = "PersonalCoach";

    private static final class PREFS_KEY {
        public static final String SCORE = "score";
        public static final String DAILY_SUMMARY_TIME = "daily_summary_time";
    }

    private static final class PREFS_DEFAULT {
        public static final int SCORE = 0;
        public static final String DAILY_SUMMARY_TIME = "12:00";
    }

    private static User mInstance;
    private static Context mContext;

    private SharedPreferences mSharedPrefs;

    public static void initialise(Context context) {
        mContext = context.getApplicationContext();
    }

    public User() {
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static User getInstance() {
        if (mInstance == null) {
            mInstance = new User();
        }
        return mInstance;
    }

    // Gets current score
    public Integer getScore() {
        return this.mSharedPrefs.getInt(PREFS_KEY.SCORE, PREFS_DEFAULT.SCORE);
    }

    public Calendar getDailySummaryTime() {
        String dailySummaryTime = this.mSharedPrefs.getString(PREFS_KEY.DAILY_SUMMARY_TIME,
                PREFS_DEFAULT.DAILY_SUMMARY_TIME);
        Integer summaryHour = Integer.parseInt(dailySummaryTime.split(":")[0]);
        Integer summaryMinute = Integer.parseInt(dailySummaryTime.split(":")[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, summaryHour);
        calendar.set(Calendar.MINUTE, summaryMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    // Updates score
    public Integer onNewSummary(Summary lastSummary) {
        Integer currentScore = this.getScore();
        Resolution resolution = lastSummary.getResolution(mContext);

        // Basic score increment algorithm
        currentScore += lastSummary.getProgress() * lastSummary.getRealDifficulty();

        // Saves new score into the shared preferences
        SharedPreferences.Editor editor = this.mSharedPrefs.edit();
        editor.putInt(PREFS_KEY.SCORE, currentScore).commit();

        return currentScore;
    }

}
