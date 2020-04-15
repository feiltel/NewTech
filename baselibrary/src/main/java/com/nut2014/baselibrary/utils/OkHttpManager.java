package com.nut2014.baselibrary.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public class OkHttpManager {
    public static final String TAG = "OkHttpManger";
    private static OkHttpClient okHttpClient;
    private volatile static OkHttpManager manager;
    private Handler mDelivery;

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getInstance() {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpManager();
                }
            }
        }
        return manager;
    }

    private void _getAsyn(String url, final ResultCallback callback) {

        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }

    private void _postFileAsyn(String url, File[] files, final ResultCallback callback, Map<String, String> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//添加文件
        if (files.length != 0) {
            for (int i = 0; i < files.length; i++) {
                RequestBody fileBody = RequestBody.create(
                        MediaType.parse(getMediaType(files[i].getName())), files[i]);
                builder.addFormDataPart("uploadfile", files[i].getName(), fileBody);
            }
        }
//添加参数
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String string = response.body().string();
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(getDecodeJSONStr(string));
                    }
                });
            }
        });
    }

    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();

        FormBody.Builder build = new FormBody.Builder();
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Log.d("key", entry.getKey() + "  " + entry.getValue());
                try {
                    jsonObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (entry.getValue() != null && entry.getKey() != null) {
                    build.add(entry.getKey(), entry.getValue());
                }
            }
        }
        Log.d("GSON", jsonObject.toString());
        FormBody formBody = build.build();
        Request request = buildPostRequest(url, formBody);
        deliveryResult(callback, request);
    }

    private Request buildPostRequest(String url, FormBody formBody) {
        return new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
    }

    /**
     * 根据文件的名称判断文件的Mine值
     */
    private String getMediaType(String fileName) {
        FileNameMap map = URLConnection.getFileNameMap();
        String contentTypeFor = map.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void deliveryResult(final ResultCallback callback, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (e != null) {
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            e.printStackTrace();
                            callback.onError(e);
                        }
                    });
                }

            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String str = response.body().string();
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {

                        callback.onResponse(getDecodeJSONStr(str));
                    }
                });
            }
        });
    }

    public static void getAsyn(String url, ResultCallback callback) {
        Log.d("get", url);
        getInstance()._getAsyn(url, callback);
    }

    public static void postAsyn(String url, ResultCallback callback, Map<String, String> params) {
        Log.d("post", url);
        getInstance()._postAsyn(url, callback, params);
    }

    public static void postFileAsyn(String url, File[] files, ResultCallback callback, Map<String, String> params) {
        getInstance()._postFileAsyn(url, files, callback, params);
    }

    public static abstract class ResultCallback {
        public abstract void onError(Exception e);

        public abstract void onResponse(String string);
    }

    public static String getDecodeJSONStr(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}
