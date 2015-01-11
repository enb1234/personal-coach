package org.brunokam.personalcoach;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ResolutionListAdapter extends ArrayAdapter<Resolution> implements LoaderManager.LoaderCallbacks<ArrayList<Resolution>> {

    private static final String LOG_TAG = "ResolutionListAdapter";

    private ResolutionListDatabase mDbHelper;

    public ResolutionListAdapter(Context context, ArrayList<Resolution> resolutionList) {
        super(context, 0, resolutionList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Resolution resolution = getItem(position);

        if (resolution.isActive()) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_resolution_active, parent, false);
            view = getActiveItemView(resolution, view);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_resolution_inactive, parent, false);
            view = getInactiveItemView(resolution, view);
        }

        return view;
    }

    @Override
    public Loader<ArrayList<Resolution>> onCreateLoader(int id, Bundle args) {
        return new ResolutionListLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Resolution>> loader, ArrayList<Resolution> resolutionList) {
        this.clear();
        this.addAll(resolutionList);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Resolution>> loader) {
        this.clear();
    }

    // TEMPORARY METHOD
    // Fills in an active item layout
    private View getActiveItemView(Resolution resolution, View view) {
        // Gets upcoming summary
        int upcomingSummaryTime = resolution.getUpcomingSummaryTime();
        String upcomingSummaryDate = Utils.formatDate(new Date(upcomingSummaryTime * 1000L));

        TextView titleView = (TextView) view.findViewById(R.id.text_view_title);
        TextView descriptionView = (TextView) view.findViewById(R.id.text_view_description);
        TextView difficultyView = (TextView) view.findViewById(R.id.text_view_difficulty_value);
        TextView upcomingSummaryView = (TextView) view.findViewById(R.id.text_view_upcoming_summary_value);

        titleView.setText(resolution.getTitle());
        descriptionView.setText(resolution.getDescription());
        difficultyView.setText(Integer.toString(resolution.getDifficulty()));
        upcomingSummaryView.setText(upcomingSummaryDate);

        return view;
    }

    // TEMPORARY METHOD
    // Fills in an inactive item layout
    private View getInactiveItemView(Resolution resolution, View view) {
        TextView titleView = (TextView) view.findViewById(R.id.text_view_title);
        TextView descriptionView = (TextView) view.findViewById(R.id.text_view_description);
        TextView difficultyView = (TextView) view.findViewById(R.id.text_view_difficulty_value);

        titleView.setText(resolution.getTitle());
        descriptionView.setText(resolution.getDescription());
        difficultyView.setText(Integer.toString(resolution.getDifficulty()));

        return view;
    }

}
