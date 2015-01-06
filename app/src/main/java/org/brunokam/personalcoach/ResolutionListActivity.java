package org.brunokam.personalcoach;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class ResolutionListActivity extends ActionBarActivity {

    private ListView resolutionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolution_list);

        this.resolutionList = (ListView) findViewById(R.id.resolution_list);
        
        // Creates adapter for resolution list
        ResolutionListAdapter adapter = new ResolutionListAdapter(getApplicationContext(), new ArrayList<Resolution>());

        // Attaches adapter to resolution list
        this.resolutionList.setAdapter(adapter);

        // Populates adapter
        adapter.refresh();

//        adapter.clear(); // For development purpose
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resolution_list, menu);
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

    public void addNewResolution(View v) {
        ResolutionListAdapter adapter = (ResolutionListAdapter) this.resolutionList.getAdapter();
        adapter.add(new Resolution("Title", "Description", 1, 1));
    }
}
