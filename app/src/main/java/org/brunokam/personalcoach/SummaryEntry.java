package org.brunokam.personalcoach;

import java.io.Serializable;

public class SummaryEntry implements Serializable {

    private Integer mResolutionID;
    private String mDescription;
    private Integer mProgress;
    private Integer mRealDifficulty;

    public SummaryEntry(Integer resolutionID, String description, Integer progress, Integer realDifficulty) {
        this.mResolutionID = resolutionID;
        this.mDescription = description;
        this.mProgress = progress;
        this.mRealDifficulty = realDifficulty;
    }

    public Integer getResolutionID() {
        return this.mResolutionID;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public Integer getProgress() {
        return this.mProgress;
    }

    public Integer getRealDifficulty() {
        return this.mRealDifficulty;
    }

    @Override
    public String toString() {
        return "SummaryEntry ["
            + "resolutionID=" + this.getResolutionID().toString() + ", "
            + "description=\"" + this.getDescription() + "\", "
            + "progress=" + this.getProgress().toString() + ", "
            + "realDifficulty=" + this.getRealDifficulty().toString()
            + "]";
    }

}
