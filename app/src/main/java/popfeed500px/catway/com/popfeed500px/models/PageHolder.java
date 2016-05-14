package popfeed500px.catway.com.popfeed500px.models;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

import popfeed500px.catway.com.popfeed500px.controllers.PageLoader;

public class PageHolder {
    int mCurrentPage = 1;
    int mMinPage = 1;

    @JsonProperty("max_page")
    int mMaxPage;
    @JsonProperty("pages")
    HashMap<Integer, Page> mPages = new HashMap<>();

    public PageHolder(){}

    public PageHolder(Context c) {
        mMaxPage = 1000;
        loadPageWithNumber(c, 1);
    }

    public Page loadPageWithNumber(Context c, int pageNumber) {
        Page newPage = PageLoader.loadPageFromResponse(PageLoader.loadPageTestJSONFromAsset(c, "page1.json"));
        mPages.put(newPage.mPageNumber, newPage);
        mPages.put(newPage.mPageNumber+1, newPage);
        return newPage;
    }


}
