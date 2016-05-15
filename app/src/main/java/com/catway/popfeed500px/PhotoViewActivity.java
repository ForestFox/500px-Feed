package com.catway.popfeed500px;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.catway.popfeed500px.controllers.PageHolderLoader;
import com.catway.popfeed500px.models.PageHolder;
import com.catway.popfeed500px.scrollgalleryview.MediaInfo;
import com.catway.popfeed500px.scrollgalleryview.ScrollGalleryView;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity {
    private ArrayList<String> images = new ArrayList<>();

    private ScrollGalleryView scrollGalleryView;
    PageHolder pageHolder;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                                mutableBitmap, "Image Description", null);
                        //Uri uri = Uri.parse(path);
                        Uri uri = getLocalBitmapUri(bitmap);

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        sendIntent.setType("image/*");
                        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.e("BitmapException", "Bitmap Failed");
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                };
                pageHolder.mPhotoPositionSelected = scrollGalleryView.getCurrentItem();
                Picasso.with(PhotoViewActivity.this)
                        .load(pageHolder.getCurrentPage().getPhotoWithIndex(pageHolder.mPhotoPositionSelected).mImageUrl)
                        .into(target);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {

            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public void onResume() {
        super.onResume();
        pageHolder = PageHolderLoader.loadPageHolderFromJSON(this);
        images = pageHolder.getCurrentPage().getUrlList();

        List<MediaInfo> infos = new ArrayList<>(images.size());
        for (String url : images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));

        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        scrollGalleryView.setPageHolder(pageHolder);
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(infos);

        scrollGalleryView.setCurrentItem(pageHolder.mPhotoPositionSelected);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            PageHolderLoader.savePageHolderToJSON(this, pageHolder);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        pageHolder.mPhotoPositionSelected = scrollGalleryView.getCurrentItem();
        PageHolderLoader.savePageHolderToJSON(this, pageHolder);
    }

}
