package com.catway.popfeed500px.controllers;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.catway.popfeed500px.FeedActivity;
import com.catway.popfeed500px.R;
import com.catway.popfeed500px.models.Page;

/**
 * Created by foxy_Cat on 15.05.2016.
 */
public class PageAdapter implements PageLoadedListener{

    Button mButtonBack;
    Button mButtonMore;
    FeedActivity mActivity;
    PageLoader mPageLoader;

    public PageAdapter(FeedActivity activity) {
        mActivity = activity;
        mPageLoader = new PageLoader();
        initButtons();
    }

    @Override
    public void onPageLoaded(final Page p) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("RUN", "Running on main thread");
                mActivity.pageHolder.setPage(p);
                invalidate();
                mActivity.locked = false;
                setButtonsEnabled(true);
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }

        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loadNewPage() {
        if (!isNetworkAvailable())
            Toast.makeText(mActivity, "No internet", Toast.LENGTH_LONG).show();
        else{
            mActivity.pageHolder.mPhotoPositionSelected = 0;
            if (mActivity.pageHolder.currentPageNotExists()) {
                int currentOrientation = mActivity.getResources().getConfiguration().orientation;
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                } else {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                }
                mPageLoader.loadPageWithNumber(mActivity.pageHolder.getCurrentPageIndex(), this);
                mActivity.locked = true;
                setButtonsEnabled(false);
            } else
                invalidate();
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        mButtonBack.setEnabled(enabled);
        mButtonMore.setEnabled(enabled);
    }


    private void invalidate() {

        updateButtonVisibility();
        mActivity.gridAdapter.notifyDataSetInvalidated();
        mActivity.updateGridAdapter();
    }

    private void initButtons() {
        mButtonBack = (Button) mActivity.findViewById(R.id.button_back);
        mButtonMore = (Button) mActivity.findViewById(R.id.button_more);

        updateButtonVisibility();
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.pageHolder.movePrev();
                loadNewPage();
            }
        });

        mButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.pageHolder.moveNext();
                loadNewPage();
            }
        });
    }

    public void updateButtonVisibility()
    {
        mButtonBack.setVisibility(mActivity.pageHolder.drawPrevButton() ? View.VISIBLE : View.INVISIBLE);
        mButtonMore.setVisibility(mActivity.pageHolder.drawNextButton() ? View.VISIBLE : View.INVISIBLE);
        mButtonBack.invalidate();
        mButtonMore.invalidate();
    }

    public void refresh()
    {
        Toast.makeText(mActivity, "REFRESH", Toast.LENGTH_SHORT).show();
        mActivity.pageHolder.deleteCurrentPage();
        loadNewPage();
    }

    public void restore() {
        mActivity.pageHolder = PageHolderLoader.loadPageHolderFromJSON(mActivity);
        mActivity.gridAdapter = new GridAdapter(mActivity, mActivity.pageHolder);
        invalidate();
    }

    public void save() {
        PageHolderLoader.savePageHolderToJSON(mActivity, mActivity.pageHolder);
    }

}
