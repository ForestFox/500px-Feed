package com.catway.popfeed500px.utils;

import android.content.Context;
import android.util.Log;

import com.catway.popfeed500px.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by foxy_Cat on 14.05.2016.
 */
public class JsonFileReader {
    public static String readFile(Context c)
    {
        StringBuilder text = new StringBuilder();
        File file = new File(c.getFilesDir(), c.getString(R.string.filename_pageholder));
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public static void prettyJson(String body) {
        ObjectMapper mapper = new ObjectMapper();
        Object json = null;
        try {
            json = mapper.readValue(body, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            Log.d("Json", indented);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
