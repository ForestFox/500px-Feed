package com.catway.popfeed500px;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.catway.popfeed500px.controllers.GridAdapter;
import com.catway.popfeed500px.controllers.PageAdapter;
import com.catway.popfeed500px.models.PageHolder;


public class FeedActivity extends AppCompatActivity {


    public GridView gridView;
    public PageHolder pageHolder;
    public GridAdapter gridAdapter;
    public PageAdapter pageAdapter;

    public boolean locked = false;

    boolean launchedFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("OnCreate", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        gridView = (GridView) findViewById(R.id.grid_photo);

        pageHolder = new PageHolder();
        gridAdapter = new GridAdapter(this, pageHolder);
        pageAdapter = new PageAdapter(this);
        if(savedInstanceState == null)
        {
            launchedFirst = true;
            pageAdapter.loadNewPage();
        }
        else
        {
            launchedFirst = false;
        }

        Log.d("Instance", savedInstanceState == null ? "null" : savedInstanceState.toString());
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
            pageAdapter.restore();
    }

    public void updateGridAdapter() {
        gridView.setAdapter(gridAdapter);
        gridView.setNumColumns(getGridColumnsNumber());
        gridView.setSelection(pageHolder.mPhotoPositionSelected);
    }

    @Override
    public void onPause() {
        Log.d("onPause", "onPause");
        super.onPause();
        pageAdapter.save();
    }

    @Override
    public void onDestroy() {
        Log.d("OnDestroy", "OnDestroy");
        super.onDestroy();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {r
        int id = item.getItemId();
        if(id == R.id.menu_refresh)
        {
            pageAdapter.refresh();
        }

        return super.onOptionsItemSelected(item);
    }
}
