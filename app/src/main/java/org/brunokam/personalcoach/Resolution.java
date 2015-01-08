package org.brunokam.personalcoach;

import java.util.Date;

public class Resolution {
    private String mTitle;
    private String mDescription;
    private int mDifficulty;
    private int mInterval;
    private int mStartTime;
    private int mLastSummaryTime;

    public Resolution(String title, String description, int difficulty, int interval, int startTime, int lastSummary) {
        this.mTitle = title;
        this.mDescription = description;
        this.mDifficulty = difficulty;
        this.mInterval = interval;
        this.mStartTime = startTime;
        this.mLastSummaryTime = lastSummary;
    }

    public Resolution(String title, String description, int difficulty, int interval, int startTime) {
        this(title, description, difficulty, interval, startTime, 0);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        this.mDifficulty = difficulty;
    }

    public int getInterval() {
        return mInterval;
    }

    public void setInterval(int interval) {
        this.mInterval = interval;
    }

    public int getStartTime() {
        return mStartTime;
    }

    public void setStartTime(int startTime) {
        this.mStartTime = startTime;
    }

    public int getLastSummaryTime() {
        return mLastSummaryTime;
    }

    public void setLastSummaryTime(int lastSummary) {
        this.mLastSummaryTime = lastSummary;
    }

    public int getUpcomingSummaryTime() {
        int intervalSeconds = this.mInterval * 24 * 3600;
        int currentTimestamp = (int) (System.currentTimeMillis() / 1000L);
        int cycle = (int) Math.floor((currentTimestamp - this.mStartTime) / intervalSeconds);

        int upcomingSummaryTime = this.mStartTime + intervalSeconds * (cycle + 1);
        return upcomingSummaryTime;
    }
}
