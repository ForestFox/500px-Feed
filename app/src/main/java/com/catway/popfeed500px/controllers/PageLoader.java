package com.catway.popfeed500px.controllers;

import android.content.Context;

import com.catway.popfeed500px.models.Page;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class PageLoader {
    public static Page loadPageFromResponse(String jsonPageResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonPageResponse);
            Page newPage = objectMapper.treeToValue(rootNode, Page.class);
            return newPage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadPageTestJSONFromAsset(Context c, String fileName) {
        String json = null;
        try {
            InputStream is = c.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
