package com.catway.popfeed500px.controllers;

import android.content.Context;

import com.catway.popfeed500px.models.Page;
import com.catway.popfeed500px.network.PageRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PageLoader implements Callback {
    PageLoadedListener pageLoadedListener;

    private static Page loadPageFromResponse(String jsonPageResponse) {
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

    public void loadPageWithNumber(int pageNum, final PageLoadedListener pageLoadedListener)
    {
        this.pageLoadedListener = pageLoadedListener;
        try {
            PageRequest.run(pageNum, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        String jsonPage = response.body().string();
        final Page page = loadPageFromResponse(jsonPage);

        pageLoadedListener.onPageLoaded(page);


    }
}
