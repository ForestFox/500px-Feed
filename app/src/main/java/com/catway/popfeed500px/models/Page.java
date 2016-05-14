package com.catway.popfeed500px.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Page {
    @JsonProperty("current_page")
    int mPageNumber;
    @JsonProperty("photos")
    private ArrayList<Photo> mPhotos = new ArrayList<>();

    public Page(){}

    @JsonIgnore
    public int getImageCount()
    {
        return mPhotos.size();
    }

    @JsonIgnore
    public Photo getPhotoWithIndex(int index)
    {
        return mPhotos.get(index);
    }

    @JsonIgnore
    public long getPhotoPosition(Photo photo)
    {
        return mPhotos.indexOf(photo);
    }

    public ArrayList<String> getUrlList()
    {
        ArrayList<String> urls = new ArrayList<>();
        for (Photo p: mPhotos
             ) {
            urls.add(p.mImageUrl);
        }
        return urls;
    }

}
