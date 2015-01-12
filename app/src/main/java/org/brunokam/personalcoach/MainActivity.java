package org.brunokam.personalcoach;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends ActionBarActivity implements Observer {

    private static String LOG_TAG = "MainActivity";

    TextView mTextViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mTextViewScore = (TextView) findViewById(R.id.text_view_score_value);

        Extras.initialise(this);
        User.initialise(this);
        SummaryBroadcastReceiver.initialise(this);

        // Attaches observer to the summary database
        SummaryDatabase.getInstance(getApplicationContext()).addObserver(this);
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

    // Shows ResolutionList activity
    public void onManageResolutionsClick(View v) {
        Intent intent = new Intent(this, ResolutionListActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data != null) {
            Summary summary = (Summary) data;
            User.getInstance().onNewSummary(summary);
            Integer score = User.getInstance().getScore();

            this.mTextViewScore.setText(score.toString());
        }
    }
}
