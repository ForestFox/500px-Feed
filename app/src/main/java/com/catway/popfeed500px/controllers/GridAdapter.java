package com.catway.popfeed500px.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catway.popfeed500px.R;
import com.catway.popfeed500px.models.PageHolder;
import com.catway.popfeed500px.models.Photo;
import com.squareup.picasso.Picasso;


public class GridAdapter extends BaseAdapter
{
    private PageHolder pageHolder;
    private LayoutInflater mInflater;
    private Context c;
    private Picasso picasso;
    public GridAdapter(Context context, PageHolder pageHolder)
    {
        this.pageHolder = pageHolder;
        c = context;
        mInflater = LayoutInflater.from(context);
        picasso = Picasso.with(c);
        picasso.setIndicatorsEnabled(true);
    }

    @Override
    public int getCount() {
        return pageHolder.getCurrentPage().getImageCount();
    }

    @Override
    public Object getItem(int i)
    {
        return pageHolder.getCurrentPage().getPhotoWithIndex(i);
    }

    @Override
    public long getItemId(int i)
    {
        return pageHolder.getCurrentPage().getPhotoPosition((Photo) getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null)
        {
            v = mInflater.inflate(R.layout.item_grid_view, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        Photo photo = (Photo) getItem(i);

        picasso.load(photo.mImageUrl)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
                .into(picture);
        return v;
    }

}