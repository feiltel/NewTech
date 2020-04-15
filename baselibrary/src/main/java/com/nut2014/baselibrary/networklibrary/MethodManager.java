package com.nut2014.baselibrary.networklibrary;

import com.nut2014.baselibrary.networklibrary.type.NetType;

import java.lang.reflect.Method;

//保存符合要求的网络监听
public class MethodManager {
    //
    private Class<?> type;
    private NetType netType;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }
}
