package com.nut2014.baselibrary.networklibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nut2014.baselibrary.networklibrary.annotaion.NetWork;
import com.nut2014.baselibrary.networklibrary.listener.NetChangeObServer;
import com.nut2014.baselibrary.networklibrary.type.NetType;
import com.nut2014.baselibrary.networklibrary.utils.Constants;
import com.nut2014.baselibrary.networklibrary.utils.NetWorkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetStateReceiver extends BroadcastReceiver {
    private NetType netType;
    private NetChangeObServer listener;

    //保存所有的注解方法
    private Map<Object, List<MethodManager>> networkList;

    public void setListener(NetChangeObServer listener) {
        this.listener = listener;
    }

    public NetStateReceiver() {
        netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.e(Constants.LOG_TAG, "异常");
            return;
        }
        //处理广播事件
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "网络改变");
            netType = NetWorkUtils.getType();
            if (NetWorkUtils.isNetWorkAvailable()) {//网络可用
                Log.e(Constants.LOG_TAG, "网络连接成功");
                if (listener != null) {
                    listener.onConnect(netType);
                }
            } else {
                Log.e(Constants.LOG_TAG, "网络连接失败");
                if (listener != null) {
                    listener.onDisConnect();
                }
            }
            post(netType);
        }

    }

    //同时分发事件
    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        //
        for (Object getter : set) {
            //所有注解的方法
            List<MethodManager> functionManagerList = networkList.get(getter);
            if (functionManagerList != null) {
                for (MethodManager functionManager : functionManagerList) {
                    if (functionManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (functionManager.getNetType()) {
                            case AUTO:
                                invoke(functionManager, getter, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(functionManager, getter, netType);
                                }
                                break;
                            case CNNET:
                                if (netType == NetType.CNNET || netType == NetType.NONE) {
                                    invoke(functionManager, getter, netType);
                                }
                                break;
                            case CNWAP:
                                if (netType == NetType.CNWAP || netType == NetType.NONE) {
                                    invoke(functionManager, getter, netType);
                                }
                                break;
                        }
                    }
                }
            }
        }

    }

    private void invoke(MethodManager functionManager, Object getter, NetType netType) {
        try {
            functionManager.getMethod().invoke(getter, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    //注册的方法添加到集合
    public void register(Object object) {
        List<MethodManager> functionManagerList = networkList.get(object);
        if (functionManagerList == null) {
            functionManagerList = findAnnotationFunction(object);
            networkList.put(object, functionManagerList);
            Log.d(Constants.LOG_TAG, networkList.size() + ">>" + functionManagerList.size());
        }
    }

    /**
     * 获取当前类所有注解方法
     *
     * @param object 当前类
     * @return
     */
    private List<MethodManager> findAnnotationFunction(Object object) {
        List<MethodManager> functionManagerList = new ArrayList<>();
        Class<?> clazz = object.getClass();
        //货物所有方法
        Method[] methods = clazz.getMethods();
        for (final Method method : methods) {
            //获取每个方法的注解
            NetWork netWork = method.getAnnotation(NetWork.class);
            if (netWork == null) {
                continue;
            }
            //方法返回值校验
            //方法参数校验


            Class<?>[] parameterTypes = method.getParameterTypes();
            Log.d("mmm", method.getName() + ">>" + parameterTypes.length);
            if (parameterTypes.length != 1) {
                Log.e(Constants.LOG_TAG, method.getName() + "方法有且只有一个参数");
                continue;
            }
            if (!parameterTypes[0].isAssignableFrom(NetType.class)) {
                Log.e(Constants.LOG_TAG, method.getName() + "方法第一个参数必须是NetType");
                continue;
            }
            //过滤方法完成

            functionManagerList.add(new MethodManager(parameterTypes[0], netWork.netType(), method));
        }
        return functionManagerList;
    }

    public void unRegister(Object object) {
        if (!networkList.isEmpty()) {
            networkList.remove(object);
        }
        Log.d(Constants.LOG_TAG, object.getClass().getName() + "注销成功");


    }

    public void unRegisterAll() {
        //应用退出时，
        if (!networkList.isEmpty()) {
            networkList.clear();
        }
        NetWorkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;

        Log.d(Constants.LOG_TAG, "注销所有成功");
    }
}
