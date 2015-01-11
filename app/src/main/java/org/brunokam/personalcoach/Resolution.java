package org.brunokam.personalcoach;

import android.content.Context;

import java.io.Serializable;

public class Resolution implements Serializable {

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
            + "ID=" + this.mID.toString() + ", "
            + "title=\"" + this.mTitle + "\", "
            + "description=\"" + this.mDescription + "\", "
            + "difficulty=" + this.mDifficulty.toString() + ", "
            + "interval=" + this.mInterval.toString() + ", "
            + "startTime=" + (this.mStartTime != null ? this.mStartTime.toString() : "null") + ", "
            + "lastSummaryTime=" + (this.mLastSummaryTime != null ? this.mLastSummaryTime.toString() : "null")
            + "]";
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

    public void start() {
        int currentTime = (int) (System.currentTimeMillis() / 1000L);
        this.mStartTime = Utils.roundTimestampToDay(currentTime);


    }

}
