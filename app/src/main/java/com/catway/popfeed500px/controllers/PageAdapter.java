package com.catway.popfeed500px.controllers;

import android.view.View;
import android.widget.Button;

import com.catway.popfeed500px.FeedActivity;
import com.catway.popfeed500px.R;

/**
 * Created by foxy_Cat on 15.05.2016.
 */
public class PageAdapter {

    Button mButtonBack;
    Button mButtonMore;
    FeedActivity mActivity;

    public PageAdapter(FeedActivity activity) {
        mActivity = activity;
        initButtons();

    }

    private void initButtons() {
        mButtonBack = (Button) mActivity.findViewById(R.id.button_back);
        mButtonMore = (Button) mActivity.findViewById(R.id.button_more);

        updateVisibility();
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.pageHolder.movePrev();
                invalidate();
            }
        });

        mButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.pageHolder.moveNext();
                invalidate();
            }
        });
    }

    private void invalidate() {
        updateVisibility();
        mActivity.gridAdapter.notifyDataSetInvalidated();
    }

    public void updateVisibility()
    {
        mButtonBack.setVisibility(mActivity.pageHolder.drawPrevButton() ? View.VISIBLE : View.INVISIBLE);
        mButtonMore.setVisibility(mActivity.pageHolder.drawNextButton() ? View.VISIBLE : View.INVISIBLE);
        mButtonBack.invalidate();
        mButtonMore.invalidate();
    }
}
