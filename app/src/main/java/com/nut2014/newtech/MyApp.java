package com.nut2014.newtech;

import android.app.Application;
import android.content.Context;

import com.nut2014.baselibrary.networklibrary.NetWorkManager;
import com.wanjian.cockroach.Cockroach;


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
