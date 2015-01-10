package org.brunokam.personalcoach;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ResolutionDetails extends ActionBarActivity {

    private static final String LOG_TAG = "ResolutionDetails";

    private Resolution mResolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mResolution = (Resolution) getIntent().getExtras().getSerializable("resolution");

        this.initialiseView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resolution_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialiseActiveView() {
        TextView titleView = (TextView) findViewById(R.id.text_view_title);
        TextView descriptionView = (TextView) findViewById(R.id.text_view_description);
        TextView difficultyView = (TextView) findViewById(R.id.text_view_difficulty_value);
        TextView intervalView = (TextView) findViewById(R.id.text_view_interval_value);
        TextView startedView = (TextView) findViewById(R.id.text_view_started_value);
        TextView upcomingSummaryView = (TextView) findViewById(R.id.text_view_upcoming_summary_value);

        titleView.setText(this.mResolution.getTitle());
        descriptionView.setText(this.mResolution.getDescription());
        difficultyView.setText(this.mResolution.getDifficulty().toString());
        intervalView.setText(this.mResolution.getInterval().toString());
        startedView.setText(Utils.formatTimestamp(this.mResolution.getStartTime()));
        upcomingSummaryView.setText(Utils.formatTimestamp(this.mResolution.getUpcomingSummaryTime()));
    }

    private void initialiseInctiveView() {
        TextView titleView = (TextView) findViewById(R.id.text_view_title);
        TextView descriptionView = (TextView) findViewById(R.id.text_view_description);
        TextView difficultyView = (TextView) findViewById(R.id.text_view_difficulty_value);
        TextView intervalView = (TextView) findViewById(R.id.text_view_interval_value);

        titleView.setText(this.mResolution.getTitle());
        descriptionView.setText(this.mResolution.getDescription());
        difficultyView.setText(this.mResolution.getDifficulty().toString());
        intervalView.setText(this.mResolution.getInterval().toString());
    }

    private void initialiseView() {
        if (this.mResolution.isActive()) {
            setContentView(R.layout.activity_resolution_details_active);
            initialiseActiveView();
        } else {
            setContentView(R.layout.activity_resolution_details_inactive);
            initialiseInctiveView();
        }
    }

}