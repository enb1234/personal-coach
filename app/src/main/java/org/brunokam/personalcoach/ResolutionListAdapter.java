package org.brunokam.personalcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ResolutionListAdapter extends ArrayAdapter<Resolution> {

    private ResolutionListDatabaseHelper mDbHelper;

    public ResolutionListAdapter(Context context, ArrayList<Resolution> resolutionList) {
        super(context, 0, resolutionList);
        this.mDbHelper = new ResolutionListDatabaseHelper(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Resolution resolution = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_resolution, parent, false);
        }

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
    
    @Override
    public void add(Resolution resolution) {
        this.mDbHelper.set(resolution);
        super.add(resolution);
    }

    @Override
    public void clear() {
        this.mDbHelper.clear();
        super.clear();
    }

    public void refresh() {
        ArrayList<Resolution> resolutions = this.mDbHelper.all();
        this.addAll(resolutions);
    }
}
