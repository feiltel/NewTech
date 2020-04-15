package com.nut2014.baselibrary.networklibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nut2014.baselibrary.networklibrary.NetWorkManager;
import com.nut2014.baselibrary.networklibrary.type.NetType;

public class NetWorkUtils {
    @SuppressLint("MissingPermission")
    public static boolean isNetWorkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) NetWorkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        NetworkInfo[] infos = connMgr.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static NetType getType() {
        ConnectivityManager connMgr = (ConnectivityManager) NetWorkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return NetType.NONE;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CNNET;
            } else {
                return NetType.CNWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
