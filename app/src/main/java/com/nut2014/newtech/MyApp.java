package com.nut2014.newtech;

import android.app.Application;
import android.content.Context;

import com.nut2014.newtech.networklibrary.NetWorkManager;
import com.nut2014.newtech.utils.PathUtils;
import com.wanjian.cockroach.Cockroach;
import com.yalantis.ucrop.util.SdkUtils;

public class MyApp extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Cockroach.init(this,Constant.crashPath,null);
        context=getApplicationContext();
        NetWorkManager.getDefault().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
