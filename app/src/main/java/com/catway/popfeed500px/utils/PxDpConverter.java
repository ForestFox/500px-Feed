package com.catway.popfeed500px.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by foxy_Cat on 15.05.2016.
 */
public class PxDpConverter {
    public static int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

    public static float px2dp(Resources resource, int px)  {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,resource.getDisplayMetrics());
    }
}
