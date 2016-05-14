package com.catway.popfeed500px.scrollgalleryview;


import com.catway.popfeed500px.scrollgalleryview.loader.MediaLoader;

/**
 * Media Info contains the information required to load and display the media in the gallery.
 */
public class MediaInfo {

    private MediaLoader mLoader;

    public static MediaInfo mediaLoader(MediaLoader mediaLoader) {
        return new MediaInfo().setLoader(mediaLoader);
    }

    public MediaLoader getLoader() {
        return mLoader;
    }

    public MediaInfo setLoader(MediaLoader loader) {
        mLoader = loader;
        return this;
    }
}
