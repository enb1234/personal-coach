package org.brunokam.personalcoach;

import java.io.Serializable;

// Represents resolution which has not been saved into the database yet
public class ResolutionEntry implements Serializable {

    private String mTitle;
    private String mDescription;
    private Integer mDifficulty;
    private Integer mInterval;
    private Integer mStartTime;
    private Integer mLastSummaryTime;

    // Constructs complete ResolutionEntry
    public ResolutionEntry(String title, String description, Integer difficulty, Integer interval, Integer startTime, Integer lastSummary) {
        this.mTitle = title;
        this.mDescription = description;
        this.mDifficulty = difficulty;
        this.mInterval = interval;
        this.mStartTime = startTime;
        this.mLastSummaryTime = lastSummary;
    }

    // Constructs ResolutionEntry without ID and lastSummary
    public ResolutionEntry(String title, String description, Integer difficulty, Integer interval, Integer startTime) {
        this(title, description, difficulty, interval, startTime, null);
    }

    // Constructs ResolutionEntry without ID, startTime and lastSummary
    public ResolutionEntry(String title, String description, Integer difficulty, Integer interval) {
        this(title, description, difficulty, interval, null, null);
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public Integer getDifficulty() {
        return this.mDifficulty;
    }

    public Integer getInterval() {
        return this.mInterval;
    }

    public Integer getStartTime() {
        return this.mStartTime;
    }

    public Integer getLastSummaryTime() {
        return this.mLastSummaryTime;
    }

    @Override
    public String toString() {
        return "ResolutionEntry ["
            + "title=\"" + this.mTitle + "\", "
            + "description=\"" + this.mDescription + "\", "
            + "difficulty=" + this.mDifficulty.toString() + ", "
            + "interval=" + this.mInterval.toString() + ", "
            + "startTime=" + (this.mStartTime != null ? this.mStartTime.toString() : "null") + ", "
            + "lastSummaryTime=" + (this.mLastSummaryTime != null ? this.mLastSummaryTime.toString() : "null")
            + "]";
    }

}
