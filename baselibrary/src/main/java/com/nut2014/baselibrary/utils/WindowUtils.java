package com.nut2014.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by admin on 2016/4/2.
 * 屏幕工具类
 */
public class WindowUtils {
    //得到屏幕像素密度
    public static float getDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
    }

    //得到屏幕像素高度
    public static int getHeightPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return (int) (metric.heightPixels);
    }

    public static int getWidthPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return (int) (metric.widthPixels);
    }

    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
