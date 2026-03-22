package com.showguan.chapter07_client.util;

import android.content.Context;

public class Utils {
    // 根据手机的分辨率从dp的单位转成px
    public static int dip2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        // scale 实际上已经包含了除以160的效果
        return (int)(dpValue * scale + 0.5f);
    }
}
