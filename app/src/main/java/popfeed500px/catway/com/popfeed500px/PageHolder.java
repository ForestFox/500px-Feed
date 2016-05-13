package popfeed500px.catway.com.popfeed500px;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by foxy_Cat on 12.05.2016.
 */
public class PageHolder {
    int mMaxPage;
    int mCurrentPage = 1;
    int mMinPage = 1;

    HashMap<Integer, Page> mPages = new HashMap<>();
    public PageHolder(Context c) {
        loadPageWithNumber(c, 1);
    }

    public void loadPageWithNumber(Context c, int pageNumber)
    {
        loadPageFromResponse(loadJSONFromAsset(c, "page1.json"));
    }

    public void loadPageFromResponse(String jsonPageResponse)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonPageResponse);
            Page newPage = objectMapper.treeToValue(rootNode, Page.class);
            mPages.put(newPage.mPageNumber, newPage);
        } catch (IOException e) {
            Log.e("TAG", e.toString());
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(Context c, String fileName) {
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
