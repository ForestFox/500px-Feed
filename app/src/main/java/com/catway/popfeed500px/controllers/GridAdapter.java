package com.catway.popfeed500px.controllers;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catway.popfeed500px.FeedActivity;
import com.catway.popfeed500px.PhotoViewActivity;
import com.catway.popfeed500px.R;
import com.catway.popfeed500px.models.PageHolder;
import com.catway.popfeed500px.models.Photo;
import com.squareup.picasso.Picasso;


public class GridAdapter extends BaseAdapter
{
    private PageHolder pageHolder;
    private LayoutInflater mInflater;
    private FeedActivity activity;
    private Picasso picasso;
    public GridAdapter(FeedActivity activity, PageHolder pageHolder)
    {
        this.pageHolder = pageHolder;
        this.activity = activity;
        mInflater = LayoutInflater.from(this.activity);
        picasso = Picasso.with(this.activity);
        //picasso.setIndicatorsEnabled(true);
    }

    @Override
    public int getCount() {
        if(pageHolder != null)
            return pageHolder.getCurrentPage().getImageCount();
        else return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return pageHolder.getCurrentPage().getPhotoWithIndex(position);
    }

    @Override
    public long getItemId(int position)
    {
        return pageHolder.getCurrentPage().getPhotoPosition((Photo) getItem(position));
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        ImageView imageView;
        TextView name;

        if(view == null)
        {
            view = mInflater.inflate(R.layout.item_grid_view, viewGroup, false);
            view.setTag(R.id.picture, view.findViewById(R.id.picture));
            view.setTag(R.id.text, view.findViewById(R.id.text));
        }
        imageView = (ImageView)view.getTag(R.id.picture);
        name = (TextView)view.getTag(R.id.text);

        Photo photo = (Photo) getItem(position);

        picasso.load(photo.mImageUrl).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.locked == false)
                {
                    pageHolder.mPhotoPositionSelected = position;
                    Log.d("GRIDADAPTER", "Current position = " + String.valueOf(pageHolder.mPhotoPositionSelected));
                    PageHolderLoader.savePageHolderToJSON(activity, pageHolder);
                    Intent intent = new Intent(activity, PhotoViewActivity.class);
                    activity.startActivity(intent);
                }
            }
        });

        return view;
    }

}