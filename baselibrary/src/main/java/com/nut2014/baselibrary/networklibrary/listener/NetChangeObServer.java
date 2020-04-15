package com.nut2014.baselibrary.networklibrary.listener;

import com.nut2014.baselibrary.networklibrary.type.NetType;

public interface NetChangeObServer {
    void onConnect(NetType type);
    void onDisConnect();
}
