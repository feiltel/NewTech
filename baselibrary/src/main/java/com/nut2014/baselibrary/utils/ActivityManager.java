package com.nut2014.baselibrary.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * @author feiltel 2020/4/20 0020
 * Activity 管理类 获取当前Activity
 */
public class ActivityManager {
    private static ActivityManager mInstance = new ActivityManager();
    private WeakReference<Activity> mCurrentActivityWeakRef;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        return mInstance;
    }

    /**
     * 获取当前 Activity
     *
     * @return 活动
     */
    public Activity getCurrentActivity() {
        if (mCurrentActivityWeakRef != null) {
            return mCurrentActivityWeakRef.get();
        }
        return null;
    }

    /**
     * 设置当前activity
     *
     * @param activity 活动
     */
    public void setCurrentActivity(Activity activity) {
        mCurrentActivityWeakRef = new WeakReference<>(activity);
    }
}
