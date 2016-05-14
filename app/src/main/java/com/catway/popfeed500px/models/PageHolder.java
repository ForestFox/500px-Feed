package com.catway.popfeed500px.models;

import android.content.Context;

import com.catway.popfeed500px.controllers.PageLoader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class PageHolder {

    @JsonProperty("photo_position_selected")
    public int mPhotoPositionSelected;
    @JsonProperty("current_page")
    private int mCurrentPage = 1;
    @JsonIgnore
    private int mMinPage = 1;
    @JsonIgnore
    Context c;
    @JsonProperty("max_page")
    private int mMaxPage;
    @JsonProperty("pages")
    private HashMap<Integer, Page> mPages = new HashMap<>();

    public PageHolder(){}

    public PageHolder(Context c) {
        this.c = c;
        mMaxPage = 1000;
        loadPageWithNumber(c, mMinPage);
    }

    public Page loadPageWithNumber(Context c, int pageNumber) {
        Page newPage = PageLoader.loadPageFromResponse(PageLoader.loadPageTestJSONFromAsset(c, "page1.json"));
        mPages.put(newPage.mPageNumber, newPage);
        return newPage;
    }

    public boolean drawPrevButton()
    {
        return mCurrentPage == mMinPage ? false:true;
    }

    public boolean drawNextButton()
    {
        return mCurrentPage == mMaxPage ? false:true;
    }

    public void moveNext()
    {
        mCurrentPage ++;
        loadPageWithNumber(c,mCurrentPage);
    }

    public void movePrev()
    {
        mCurrentPage --;
        loadPageWithNumber(c,mCurrentPage);
    }

    @JsonIgnore
    public Page getCurrentPage()
    {
        return mPages.get(mCurrentPage);
    }
}
