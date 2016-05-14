package com.catway.popfeed500px.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Page {
    @JsonProperty("current_page")
    int mPageNumber;
    @JsonProperty("photos")
    ArrayList<Photo> mImages = new ArrayList<>();

    public Page()
    {

    }

    public Page(String jsonPageResponse) {

    }

}
