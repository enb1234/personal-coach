package org.brunokam.personalcoach;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ResolutionListActivity extends ActionBarActivity implements AddResolutionFragment.AddResolutionFragmentListener {

    private static final String LOG_TAG = "ResolutionListActivity";

    private ListView mResolutionList;
    private boolean mIsLargeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolution_list);

        this.mResolutionList = (ListView) findViewById(R.id.resolution_list);
        this.mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
        
        // Creates adapter for the resolution list
        ResolutionListAdapter adapter = new ResolutionListAdapter(getApplicationContext(), new ArrayList<Resolution>());

        // Attaches adapter to the resolution list
        this.mResolutionList.setAdapter(adapter);

        // Attaches onItemClick listener to the resolution list
        this.mResolutionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Resolution resolution = (Resolution) parent.getAdapter().getItem(position);

                Intent intent = new Intent(ResolutionListActivity.this, ResolutionDetails.class);
                intent.putExtra("resolution", resolution);
                ResolutionListActivity.this.startActivity(intent);
            }
        });

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

    // Called after the AddResolution fragment succeeds
    public void onAddResolutionSuccess(DialogFragment dialog, Resolution resolution) {
        ResolutionListAdapter adapter = (ResolutionListAdapter) this.mResolutionList.getAdapter();
        adapter.add(resolution);
    }

    // Called after the AddResolution fragment fails
    public void onAddResolutionError(DialogFragment dialog) {
        // TODO: Implement
    }

    public void showAddResolutionFragment(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        AddResolutionFragment fragment = new AddResolutionFragment();

        if (this.mIsLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            fragment.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, fragment).addToBackStack(null).commit();
        }
    }

}
