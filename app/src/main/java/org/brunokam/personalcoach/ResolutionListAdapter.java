package org.brunokam.personalcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ResolutionListAdapter extends ArrayAdapter<Resolution> {

    private ResolutionListDatabaseHelper dbHelper;

    public ResolutionListAdapter(Context context, ArrayList<Resolution> resolutionList) {
        super(context, 0, resolutionList);
        this.dbHelper = new ResolutionListDatabaseHelper(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Resolution resolution = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.resolution_list_item, parent, false);
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);

        titleView.setText(resolution.getTitle());

        return view;
    }
    
    @Override
    public void add(Resolution resolution) {
        this.dbHelper.set(resolution);
        super.add(resolution);
    }

    @Override
    public void clear() {
        this.dbHelper.clear();
        super.clear();
    }

    public void refresh() {
        ArrayList<Resolution> resolutions = this.dbHelper.all();
        this.addAll(resolutions);
    }
}
