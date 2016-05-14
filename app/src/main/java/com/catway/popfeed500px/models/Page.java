package com.catway.popfeed500px.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Page {
    @JsonProperty("current_page")
    int mPageNumber;
    @JsonProperty("photos")
    private ArrayList<Photo> mImages = new ArrayList<>();

    public Page(){}

    @JsonIgnore
    public int getImageCount()
    {
        return mImages.size();
    }

    @JsonIgnore
    public Photo getPhotoWithIndex(int index)
    {
        return mImages.get(index);
    }

    @JsonIgnore
    public long getPhotoPosition(Photo photo)
    {
        return mImages.indexOf(photo);
    }

}
