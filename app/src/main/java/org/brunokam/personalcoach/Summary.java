package org.brunokam.personalcoach;

import android.content.Context;

import java.io.Serializable;

public class Summary implements Serializable {

    private static final String LOG_TAG = "Summary";

    private Integer mID;
    private Integer mResolutionID;
    private Integer mDate;
    private String mDescription;
    private Integer mProgress;
    private Integer mRealDifficulty;

    // Constructs complete Summary
    public Summary(Integer ID, Integer resolutionID, Integer date, String description, Integer progress, Integer realDifficulty) {
        this.mID = ID;
        this.mResolutionID = resolutionID;
        this.mDate = date;
        this.mDescription = description;
        this.mProgress = progress;
        this.mRealDifficulty = realDifficulty;
    }

    // Constructs Summary based on summary entry and ID
    public Summary(Integer ID, Integer date, SummaryEntry entry) {
        this(ID, entry.getResolutionID(), date, entry.getDescription(), entry.getProgress(), entry.getRealDifficulty());
    }

    public Integer getID() {
        return this.mID;
    }

    public Integer getResolutionID() {
        return this.mResolutionID;
    }

    public void setResolutionID(Integer resolutionID) {
        this.mResolutionID = resolutionID;
    }

    public Integer getDate() {
        return this.mDate;
    }

    public void setDate(Integer date) {
        this.mDate = date;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Integer getProgress() {
        return this.mProgress;
    }

    public void setProgress(Integer progress) {
        this.mProgress = progress;
    }

    public Integer getRealDifficulty() {
        return this.mRealDifficulty;
    }

    public void setRealDifficulty(Integer realDifficulty) {
        this.mRealDifficulty = realDifficulty;
    }

    @Override
    public String toString() {
        return "Summary ["
            + "ID=" + this.getID().toString() + ", "
            + "resolutionID=" + this.getResolutionID().toString() + ", "
            + "description=\"" + this.getDescription() + "\", "
            + "progress=" + this.getProgress().toString() + ", "
            + "realDifficulty=" + this.getRealDifficulty().toString()
            + "]";
    }

    public Resolution getResolution(Context context) {
        return ResolutionDatabase.getInstance(context).get(this.getResolutionID());
    }

}
