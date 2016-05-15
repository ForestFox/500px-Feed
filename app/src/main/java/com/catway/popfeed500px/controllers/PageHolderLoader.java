package com.catway.popfeed500px.controllers;

import android.content.Context;

import com.catway.popfeed500px.R;
import com.catway.popfeed500px.models.PageHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class PageHolderLoader {

    public static void savePageHolderToJSON(Context c, PageHolder pageHolder) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(c.getFilesDir(), c.getString(R.string.filename_pageholder)), pageHolder);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static PageHolder loadPageHolderFromJSON(Context c) {
        ObjectMapper mapper = new ObjectMapper();
        PageHolder pageHolder = null;
        try {
            pageHolder = mapper.readValue(new File(c.getFilesDir(), c.getString(R.string.filename_pageholder)), PageHolder.class);
        }
        catch (Exception e) { e.printStackTrace();}

        return pageHolder;
    }



}
