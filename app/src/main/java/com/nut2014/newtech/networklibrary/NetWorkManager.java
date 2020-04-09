package com.nut2014.newtech.networklibrary;

import android.app.Application;
import android.content.IntentFilter;

import com.nut2014.newtech.networklibrary.listener.NetChangeObServer;
import com.nut2014.newtech.networklibrary.utils.Constants;

public class NetWorkManager {
    private static volatile NetWorkManager instance;
    private NetStateReceiver receiver;
    private Application application;

    private NetWorkManager() {
        receiver = new NetStateReceiver();
    }

    public void setListener(NetChangeObServer listener) {
        receiver.setListener(listener);
    }

    public static NetWorkManager getDefault() {
        if (instance == null) {
            synchronized (NetWorkManager.class) {
                if (instance == null) {
                    instance = new NetWorkManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        return application;
    }

    public void init(Application application) {
        this.application = application;
        //动态注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver,filter);
    }

    //注册
    public void registerObserver(Object object){
        receiver.register(object);
    }
    //解除注册
    public void unRegisterObserver(Object object){
        receiver.unRegister(object);
    }
}
