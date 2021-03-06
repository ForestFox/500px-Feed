package com.catway.popfeed500px.scrollgalleryview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catway.popfeed500px.R;
import com.catway.popfeed500px.models.Photo;
import com.catway.popfeed500px.photoview.PhotoViewAttacher;
import com.catway.popfeed500px.scrollgalleryview.loader.MediaLoader;


public class ImageFragment extends Fragment {

    private MediaInfo mMediaInfo;
    private Photo mPhoto;
    private HackyViewPager viewPager;
    private ImageView backgroundImage;
    private PhotoViewAttacher photoViewAttacher;
    private TextView photoAuthorCamera;
    public void setMediaInfo(Photo photo, MediaInfo mediaInfo) {
        mPhoto = photo;
        mMediaInfo = mediaInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.image_fragment, container, false);
        backgroundImage = (ImageView) rootView.findViewById(R.id.backgroundImage);
        viewPager = (HackyViewPager) getActivity().findViewById(R.id.viewPager);
        photoAuthorCamera = (TextView) rootView.findViewById(R.id.photo_author_camera);

        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(Constants.IS_LOCKED, false);
            viewPager.setLocked(isLocked);
            backgroundImage.setImageBitmap((Bitmap) savedInstanceState.getParcelable(Constants.IMAGE));
            createViewAttacher(savedInstanceState);
        }
        photoAuthorCamera.setText( "      " + mPhoto.mName
                + " (c) " + mPhoto.getUserName()
                + (mPhoto.mCamera != null ? " taken with " + mPhoto.mCamera : ""));

        loadImageToView();


        return rootView;
    }

    private void loadImageToView() {
        if (mMediaInfo != null) {
            mMediaInfo.getLoader().loadMedia(getActivity(), backgroundImage, new MediaLoader.SuccessCallback() {
                @Override
                public void onSuccess() {
                    createViewAttacher(getArguments());
                }
            });
        }
    }

    private void createViewAttacher(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean(Constants.ZOOM)) {
            photoViewAttacher = new PhotoViewAttacher(backgroundImage);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(Constants.IS_LOCKED, viewPager.isLocked());
        }
        outState.putParcelable(Constants.IMAGE, ((BitmapDrawable) backgroundImage.getDrawable()).getBitmap());
        outState.putBoolean(Constants.ZOOM, photoViewAttacher != null);
        super.onSaveInstanceState(outState);
    }

    private boolean isViewPagerActive() {
        return viewPager != null;
    }
}
