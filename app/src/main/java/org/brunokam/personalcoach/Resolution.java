package org.brunokam.personalcoach;

import java.io.Serializable;

public class Resolution implements Serializable {

    private String mTitle;
    private String mDescription;
    private Integer mDifficulty;
    private Integer mInterval;
    private Integer mStartTime;
    private Integer mLastSummaryTime;

    public Resolution(String title, String description, Integer difficulty, Integer interval, Integer startTime, Integer lastSummary) {
        this.mTitle = title;
        this.mDescription = description;
        this.mDifficulty = difficulty;
        this.mInterval = interval;
        this.mStartTime = startTime;
        this.mLastSummaryTime = lastSummary;
    }

    public Resolution(String title, String description, Integer difficulty, Integer interval, Integer startTime) {
        this(title, description, difficulty, interval, startTime, null);
    }

    public Resolution(String title, String description, Integer difficulty, Integer interval) {
        this(title, description, difficulty, interval, null, null);
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

    public Integer getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.mDifficulty = difficulty;
    }

    public Integer getInterval() {
        return mInterval;
    }

    public void setInterval(Integer interval) {
        this.mInterval = interval;
    }

    public Integer getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Integer startTime) {
        this.mStartTime = startTime;
    }

    public Integer getLastSummaryTime() {
        return mLastSummaryTime;
    }

    public void setLastSummaryTime(Integer lastSummary) {
        this.mLastSummaryTime = lastSummary;
    }

    public boolean isActive() {
        return this.mStartTime != null && this.mStartTime != 0;
    }

    public Integer getUpcomingSummaryTime() {
        if (this.isActive()) {
            Integer intervalSeconds = this.mInterval * 24 * 3600;
            Integer currentTimestamp = (int) (System.currentTimeMillis() / 1000L);
            Integer cycle = (int) Math.floor((currentTimestamp - this.mStartTime) / intervalSeconds);

            return this.mStartTime + intervalSeconds * (cycle + 1);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Resolution [title=\"" + this.mTitle + "\""
                + ", description=\"" + this.mDescription + "\""
                + ", difficulty=" + this.mDifficulty.toString()
                + ", interval=" + this.mInterval.toString()
                + ", startTime=" + this.mStartTime.toString()
                + ", lastSummaryTime=" + this.mLastSummaryTime.toString()
                + "]";
    }

}
