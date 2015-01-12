package org.brunokam.personalcoach;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Resolution implements Serializable {

    private static final String LOG_TAG = "Resolution";

    private Integer mID;
    private String mTitle;
    private String mDescription;
    private Integer mDifficulty;
    private Integer mInterval;
    private Integer mStartTime;
    private Integer mLastSummaryTime;

    // Constructs complete Resolution
    public Resolution(Integer ID, String title, String description, Integer difficulty, Integer interval, Integer startTime, Integer lastSummary) {
        this.mID = ID;
        this.mTitle = title;
        this.mDescription = description;
        this.mDifficulty = difficulty;
        this.mInterval = interval;
        this.mStartTime = startTime;
        this.mLastSummaryTime = lastSummary;
    }

    // Constructs Resolution without lastSummary
    public Resolution(Integer ID, String title, String description, Integer difficulty, Integer interval, Integer startTime) {
        this(ID, title, description, difficulty, interval, startTime, null);
    }

    // Constructs Resolution without startTime and lastSummary
    public Resolution(Integer ID, String title, String description, Integer difficulty, Integer interval) {
        this(ID, title, description, difficulty, interval, null, null);
    }

    // Constructs Resolution based on resolution entry and ID
    public Resolution(Integer ID, ResolutionEntry entry) {
        this(ID, entry.getTitle(), entry.getDescription(), entry.getDifficulty(), entry.getInterval(),
                entry.getStartTime(), entry.getLastSummaryTime());
    }

    public Integer getID() {
        return this.mID;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Integer getDifficulty() {
        return this.mDifficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.mDifficulty = difficulty;
    }

    public Integer getInterval() {
        return this.mInterval;
    }

    public void setInterval(Integer interval) {
        this.mInterval = interval;
    }

    public Integer getStartTime() {
        return this.mStartTime;
    }

    public void setStartTime(Integer startTime) {
        this.mStartTime = startTime;
    }

    public Integer getLastSummaryTime() {
        return this.mLastSummaryTime;
    }

    public void setLastSummaryTime(Integer lastSummary) {
        this.mLastSummaryTime = lastSummary;
    }

    @Override
    public String toString() {
        return "Resolution ["
            + "ID=" + this.getID().toString() + ", "
            + "title=\"" + this.getTitle() + "\", "
            + "description=\"" + this.getDescription() + "\", "
            + "difficulty=" + this.getDifficulty().toString() + ", "
            + "interval=" + this.getInterval().toString() + ", "
            + "startTime=" + (this.getStartTime() != null ? this.getStartTime().toString() : "null") + ", "
            + "lastSummaryTime=" + (this.getLastSummaryTime() != null ? this.getLastSummaryTime().toString() : "null")
            + "]";
    }

    public boolean isActive() {
        return this.getStartTime() != null && this.getStartTime() != 0;
    }

    public boolean isSummaryTimeToday() {
        if (this.isActive()) {
            int intervalSeconds = this.getInterval() * 24 * 3600;
            int todayTimestamp = Extras.roundTimestampToDay((int) (System.currentTimeMillis() / 1000L));
            int baseStartTime = this.getStartTime();

            int delta = todayTimestamp - baseStartTime;

            if (delta < 0) {
                delta = 0;
            }

            double cycle = delta / (double) intervalSeconds;

            return Double.compare(cycle, Math.floor(cycle)) == 0;
        }

        return false;
    }

    public Integer getUpcomingSummaryTime() {
        if (this.isActive()) {
            Calendar dailySummaryTime = User.getInstance().getDailySummaryTime();
            Integer summaryHour = dailySummaryTime.get(Calendar.HOUR_OF_DAY);
            Integer summaryMinute = dailySummaryTime.get(Calendar.MINUTE);
            Log.i(LOG_TAG, summaryHour.toString() + ":" + summaryMinute.toString());

            int intervalSeconds = this.getInterval() * 24 * 3600;
            int currentTimestamp = (int) (System.currentTimeMillis() / 1000L);
            int baseStartTime = this.getStartTime() + (summaryHour * 3600) + (summaryMinute * 60);

            int delta = currentTimestamp - baseStartTime;

            if (delta < 0) {
                delta = 0;
            }

            int cycle = (int) Math.floor(delta / (double) intervalSeconds) + 1;

            int summaryTime = baseStartTime + (intervalSeconds * cycle);

            if (summaryTime < currentTimestamp) {
                summaryTime += intervalSeconds;
            }

            return summaryTime;
        }

        return null;
    }

    public void start() {
        int currentTime = (int) (System.currentTimeMillis() / 1000L);
        this.setStartTime(Extras.roundTimestampToDay(currentTime));
    }

}
