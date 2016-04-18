package org.brunokam.personalcoach;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class SummaryListActivity extends ActionBarActivity {

    private static final String LOG_TAG = "SummaryListActivity";

    private Resolution mResolution;
    private ListView mSummaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_list);

        this.mResolution = (Resolution) getIntent().getExtras().getSerializable("resolution");

        this.mSummaryList = (ListView) findViewById(R.id.summary_list);
        
        // Creates adapter for the summary list
        SummaryListAdapter adapter = new SummaryListAdapter(getApplicationContext(), new ArrayList<Summary>());

        // Attaches adapter to the summary list
        this.mSummaryList.setAdapter(adapter);
        adapter.addAll(SummaryDatabase.getInstance(this).all(this.mResolution.getID()));

        // Attaches onItemClick listener to the resolution list
//        this.mSummaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Resolution resolution = (Resolution) parent.getAdapter().getItem(position);
//
//                Intent intent = new Intent(SummaryListActivity.this, ResolutionDetailsActivity.class);
//                intent.putExtra("resolution", resolution);
//                SummaryListActivity.this.startActivity(intent);
//            }
//        });

//        getLoaderManager().initLoader(0, null, adapter);

//        adapter.clear(); // For development purpose
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
