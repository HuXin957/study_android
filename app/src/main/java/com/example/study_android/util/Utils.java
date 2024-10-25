package com.example.study_android.util;

import android.content.Context;

public class Utils {

    // 根据手机的分辨率从 dp 的单位转为 px （像素）
    public static int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）
        float scale = context.getResources().getDisplayMetrics().density;
        // 四舍五入取整
        return (int) (dpValue * scale + 0.5f);
    }
}
