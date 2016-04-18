package org.brunokam.personalcoach;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class SummaryListAdapter extends ArrayAdapter<Summary> {

    private static final String LOG_TAG = "SummaryListAdapter";

    public SummaryListAdapter(Context context, ArrayList<Summary> summaryList) {
        super(context, 0, summaryList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Summary summary = getItem(position);

        view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_summary, parent, false);
        view = getItemView(summary, view);

        return view;
    }

    // TEMPORARY METHOD
    // Fills in an item layout
    private View getItemView(Summary summary, View view) {
        TextView dateView = (TextView) view.findViewById(R.id.text_view_date);
        TextView descriptionView = (TextView) view.findViewById(R.id.text_view_description);
        TextView progressView = (TextView) view.findViewById(R.id.text_view_progress_value);
        TextView realDifficultyView = (TextView) view.findViewById(R.id.text_view_real_difficulty_value);

        dateView.setText(Extras.formatTimestamp(summary.getDate()));
        descriptionView.setText(summary.getDescription());
        progressView.setText(summary.getProgress().toString());
        realDifficultyView.setText(summary.getRealDifficulty().toString());

        return view;
    }

}
