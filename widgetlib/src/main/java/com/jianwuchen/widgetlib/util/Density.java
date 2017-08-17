package com.jianwuchen.widgetlib.util;

import android.content.res.Resources;

/**
 * Created by jove.chen on 2017/8/17.
 */

public class Density {
    private static float density = 1;
    static {
        density = Resources.getSystem().getDisplayMetrics().density;
    }
    public static int dp2px(float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }
}
