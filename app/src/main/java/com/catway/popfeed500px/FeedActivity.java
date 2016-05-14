package com.catway.popfeed500px;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import com.catway.popfeed500px.controllers.PageHolderLoader;
import com.catway.popfeed500px.models.PageHolder;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        GridLayout grid = (GridLayout) findViewById(R.id.grid_photo);
        grid.setColumnCount(3);

        Picasso.Listener listener = new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e("TAG", exception.toString());
            }
        };

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener(listener);
        Picasso picasso = builder.build();
        picasso.setIndicatorsEnabled(true);
                picasso.load("https://drscdn.500px.org/photo/153317621/q%3D50_w%3D140_h%3D140/87f074ec787f1f855a798a2ec4363851?v=4")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into((ImageView) findViewById(R.id.imageView1));
        picasso.load("https://drscdn.500px.org/photo/153317621/q%3D50_w%3D140_h%3D140/87f074ec787f1f855a798a2ec4363851?v=4")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into((ImageView) findViewById(R.id.imageView2));
        picasso.load("https://drscdn.500px.org/photo/153317621/q%3D50_w%3D140_h%3D140/87f074ec787f1f855a798a2ec4363851?v=4")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into((ImageView) findViewById(R.id.imageView3));

        PageHolder pageHolder = new PageHolder(getApplicationContext());
        PageHolderLoader.savePageHolderToJSON(getApplicationContext(), pageHolder);
        PageHolder savedPageHolder = PageHolderLoader.loadPageHolderFromJSON(getApplicationContext());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
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
}
