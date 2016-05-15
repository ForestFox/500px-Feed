package com.catway.popfeed500px.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class PageHolder {

    @JsonProperty("photo_position_selected")
    public int mPhotoPositionSelected;
    @JsonProperty("current_page")
    private int mCurrentPageIndex = 1;
    @JsonIgnore
    private int mMinPage = 1;
    @JsonProperty("max_page")
    private int mMaxPage = 1000;
    @JsonProperty("pages")
    private HashMap<Integer, Page> mPages = new HashMap<>();

    public PageHolder(){}

    public boolean drawPrevButton()
    {
        return mCurrentPageIndex == mMinPage ? false:true;
    }

    public boolean drawNextButton()
    {
        return mCurrentPageIndex == mMaxPage ? false:true;
    }

    public void moveNext()
    {
        mCurrentPageIndex++;
    }

    public void movePrev()
    {
        mCurrentPageIndex--;
    }

    @JsonIgnore
    public void setPage(Page p)
    {
        mPages.put(p.mPageNumber, p);
    }

    @JsonIgnore
    public Page getCurrentPage()
    {
        return mPages.get(mCurrentPageIndex);
    }

    @JsonIgnore
    public int getCurrentPageIndex() {
        return mCurrentPageIndex;
    }

    @JsonIgnore
    public void setMaxPage(int maxPage) {
        mMaxPage = maxPage;
    }

    @JsonIgnore
    public boolean currentPageNotExists()
    {
        return mPages.get(mCurrentPageIndex) == null;
    }

    public void deleteCurrentPage()
    {
        mPages.remove(mCurrentPageIndex);
    }

}
