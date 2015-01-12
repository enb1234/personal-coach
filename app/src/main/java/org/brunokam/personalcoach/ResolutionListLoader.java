package org.brunokam.personalcoach;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ResolutionListLoader extends AsyncTaskLoader<ArrayList<Resolution>> implements Observer {

    private static final String LOG_TAG = "ResolutionListLoader";

    private ArrayList<Resolution> mData;
    private boolean isRegistered;

    public ResolutionListLoader(Context context) {
        super(context);
        this.isRegistered = false;
    }

    @Override
    public ArrayList<Resolution> loadInBackground() {
        return ResolutionDatabase.getInstance(getContext()).all();
    }

    @Override
    public void deliverResult(ArrayList<Resolution> data) {
        if (isReset()) {
            return;
        }

        // Holds a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        ArrayList<Resolution> oldData = this.mData;
        this.mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (this.mData != null) {
            deliverResult(this.mData);
        }

        if (!this.isRegistered) {
            ResolutionDatabase.getInstance(getContext()).addObserver(this);
            this.isRegistered = true;
        }

        if (takeContentChanged() || this.mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<Resolution> data) {
        super.onCanceled(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (this.mData != null) {
            this.mData = null;
        }
    }

    // Observer method
    @Override
    public void update(Observable observable, Object data) {
        this.onContentChanged();
    }
}
