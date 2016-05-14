package com.catway.popfeed500px.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo {

    @JsonProperty("camera")
    public String mCamera;
    @JsonProperty("id")
    public int mId;
    @JsonProperty("image_format")
    public String mImageFormat;
    @JsonProperty("image_url")
    public String mImageUrl;
    @JsonProperty("user")
    User mUser;

    @JsonIgnore
    public String getUserName() {
        return mUser.mFullName;
    }
}
