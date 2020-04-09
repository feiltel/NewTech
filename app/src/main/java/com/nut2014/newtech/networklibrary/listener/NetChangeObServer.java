package com.nut2014.newtech.networklibrary.listener;

import com.nut2014.newtech.networklibrary.type.NetType;

public interface NetChangeObServer {
    void onConnect(NetType type);
    void onDisConnect();
}
