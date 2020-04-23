package com.nut2014.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nut2014.baselibrary.utils.ActivityManager;

/**
 * @author feiltel 2020/4/20 0020
 */
public class BaseApplication extends Application {
    private int activityCounter = 0;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                activityCounter++;
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                activityCounter--;
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }


    public boolean getAppFrontRunning() {
        return activityCounter > 0;
    }
}
