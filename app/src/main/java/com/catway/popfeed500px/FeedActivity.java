package com.catway.popfeed500px;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.GridView;

import com.catway.popfeed500px.controllers.GridAdapter;
import com.catway.popfeed500px.models.PageHolder;

public class FeedActivity extends AppCompatActivity {


    GridView gridView;
    PageHolder pageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        gridView = (GridView) findViewById(R.id.grid_photo);

        pageHolder = new PageHolder(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        //pageHolder = PageHolderLoader.loadPageHolderFromJSON(this);


        gridView.setAdapter(new GridAdapter(this, pageHolder));
        gridView.setNumColumns(2);
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
