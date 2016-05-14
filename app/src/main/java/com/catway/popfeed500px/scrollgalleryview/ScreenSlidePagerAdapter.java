package com.catway.popfeed500px.scrollgalleryview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.catway.popfeed500px.models.PageHolder;

import java.util.List;

/**
 * Created by veinhorn on 29.8.15.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<MediaInfo> mListOfMedia;
    private PageHolder mPageHolder;

    private boolean isZoom = false;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<MediaInfo> listOfMedia,
                                   PageHolder pageHolder, boolean isZoom) {
        super(fm);
        this.mListOfMedia = listOfMedia;
        mPageHolder = pageHolder;
        this.isZoom = isZoom;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < mListOfMedia.size()) {
            MediaInfo mediaInfo = mListOfMedia.get(position);
            fragment = loadImageFragment(mediaInfo, position);
        }
        return fragment;
    }

    private Fragment loadImageFragment(MediaInfo mediaInfo, int position)
    {
        mPageHolder.mPhotoPositionSelected = position;
        ImageFragment fragment = new ImageFragment();
        fragment.setMediaInfo(mPageHolder.getCurrentPage().getPhotoWithIndex(position), mediaInfo);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ZOOM, isZoom);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mListOfMedia.size();
    }
}
