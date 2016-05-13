package popfeed500px.catway.com.popfeed500px;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by foxy_Cat on 12.05.2016.
 */
public class Page {
    @JsonProperty("current_page")
    int mPageNumber;
    @JsonProperty("photos")
    ArrayList<Photo> mImageIds = new ArrayList<>();

    public Page()
    {

    }

    public Page(String jsonPageResponse) {

    }

}
