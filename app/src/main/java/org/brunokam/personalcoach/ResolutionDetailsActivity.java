package org.brunokam.personalcoach;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ResolutionDetailsActivity extends ActionBarActivity {

    private static final String LOG_TAG = "ResolutionDetailsActivity";

    private Resolution mResolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mResolution = (Resolution) getIntent().getExtras().getSerializable("resolution");

        this.updateView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateActiveView() {
        TextView startedView = (TextView) findViewById(R.id.text_view_started_value);
        TextView upcomingSummaryView = (TextView) findViewById(R.id.text_view_upcoming_summary_value);

        startedView.setText(Extras.formatTimestamp(this.mResolution.getStartTime()));
        upcomingSummaryView.setText(Extras.formatTimestamp(this.mResolution.getUpcomingSummaryTime()));
    }

    private void updateView() {
        if (this.mResolution.isActive()) {
            setContentView(R.layout.activity_resolution_details_active);
            updateActiveView();
        } else {
            setContentView(R.layout.activity_resolution_details_inactive);
        }

        TextView titleView = (TextView) findViewById(R.id.text_view_title);
        TextView descriptionView = (TextView) findViewById(R.id.text_view_description);
        TextView difficultyView = (TextView) findViewById(R.id.text_view_difficulty_value);
        TextView intervalView = (TextView) findViewById(R.id.text_view_interval_value);

        titleView.setText(this.mResolution.getTitle());
        descriptionView.setText(this.mResolution.getDescription());
        difficultyView.setText(this.mResolution.getDifficulty().toString());
        intervalView.setText(this.mResolution.getInterval().toString());
    }

    public void onStartResolutionClick(View view) {
        this.mResolution.start();
        ResolutionDatabase.getInstance(this).update(this.mResolution);
        this.updateView();
    }

    // TEMPORARY METHOD
    public void onStopResolutionClick(View view) {
        this.mResolution.setStartTime(null);
        this.mResolution.setLastSummaryTime(null);
        ResolutionDatabase.getInstance(this).update(this.mResolution);
        SummaryDatabase.getInstance(this).clear(this.mResolution.getID());
        this.updateView();
    }

    // TEMPORARY METHOD
    public void onDeleteResolutionClick(View view) {
        ResolutionDatabase.getInstance(this).delete(this.mResolution);
        SummaryDatabase.getInstance(this).clear(this.mResolution.getID());
        finish();
    }

    // TEMPORARY METHOD
    public void onSpeedupResolutionClick(View view) {
        Integer startTime = this.mResolution.getStartTime();
        this.mResolution.setStartTime(startTime - (this.mResolution.getInterval() * 24 * 3600));
        ResolutionDatabase.getInstance(this).update(this.mResolution);
        this.updateActiveView();
    }

    public void onViewSummariesClick(View view) {
        Intent intent = new Intent(this, SummaryListActivity.class);
        intent.putExtra("resolution", this.mResolution);
        startActivity(intent);
    }

}
