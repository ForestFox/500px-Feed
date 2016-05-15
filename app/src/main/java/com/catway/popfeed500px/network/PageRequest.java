package com.catway.popfeed500px.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PageRequest {
    static final OkHttpClient client = new OkHttpClient();

    public static void run(int pageNum, Callback callback) throws Exception {
        Request request = new Request.Builder()
                .url("https://api.500px.com/v1/photos?feature=popular&consumer_key=wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF&page=" + String.valueOf(pageNum) )
                .build();

        client.newCall(request).enqueue(callback);
    }
}
