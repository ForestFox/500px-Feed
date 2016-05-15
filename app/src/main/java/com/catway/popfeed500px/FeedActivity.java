package com.catway.popfeed500px;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.catway.popfeed500px.controllers.GridAdapter;
import com.catway.popfeed500px.controllers.PageAdapter;
import com.catway.popfeed500px.controllers.PageHolderLoader;
import com.catway.popfeed500px.models.PageHolder;

public class FeedActivity extends AppCompatActivity {


    public GridView gridView;
    public PageHolder pageHolder;
    public GridAdapter gridAdapter;
    public PageAdapter pageAdapter;

    boolean launchedFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("OnCreate", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        gridView = (GridView) findViewById(R.id.grid_photo);


        if(savedInstanceState == null)
        {
            launchedFirst = true;
            pageHolder = new PageHolder(getApplicationContext());
        }
        else
        {
            launchedFirst = false;
        }

        Log.d("Instance", savedInstanceState == null? "null" : savedInstanceState.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("No matter", "No matter");
    }


    @Override
    public void onResume() {
        Log.d("onResume", "onResume");
        super.onResume();
        if(!launchedFirst)
            pageHolder = PageHolderLoader.loadPageHolderFromJSON(this);

        updateGridAdapter();
        pageAdapter = new PageAdapter(this);
    }

    public void updateGridAdapter() {
        gridAdapter = new GridAdapter(this, pageHolder);
        gridView.setAdapter(gridAdapter);
        gridView.setNumColumns(getGridColumnsNumber());
        gridView.setSelection(pageHolder.mPhotoPositionSelected);
    }

    @Override
    public void onPause() {
        super.onPause();
        PageHolderLoader.savePageHolderToJSON(this, pageHolder);
        Log.d("onPause", "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("OnDestroy", "OnDestroy");
    }

    public int getGridColumnsNumber()
    {
        int res;
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            res = R.integer.columns_landscape;
        else
            res = R.integer.columns_portrait;
        return getResources().getInteger(res);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
