package com.nut2014.baselibrary.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * @author feiltel 2020/4/20 0020
 */
public class ActivityManager {
    private static ActivityManager mInstance = new ActivityManager();
    private WeakReference<Activity> mCurrentActivityWeakRef;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        return mInstance;
    }

    public Activity getCurrentActivity() {
        if (mCurrentActivityWeakRef != null) {
            return mCurrentActivityWeakRef.get();
        }
        return null;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivityWeakRef = new WeakReference<>(activity);
    }
}
