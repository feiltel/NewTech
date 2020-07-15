package com.nut2014.newtech;

import com.nut2014.baselibrary.base.BaseApplication;
import com.nut2014.baselibrary.http.RetrofitManager;
import com.nut2014.baselibrary.networklibrary.NetWorkManager;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.newtech.workManager.LogUploadWorker;

import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //异常捕获
        //   Cockroach.init(this, Constant.crashPath, null);
        //网络状态监听类注册
        NetWorkManager.getDefault().init(this);
        // Retrofit 初始化
        final String BASE_URL = "http://192.168.31.196:8080";
        RetrofitManager.getInstance().init(BASE_URL, authorization);
        //运行日志上传
        LogUploadWorker.runUploadLog(getApplicationContext());
    }

    //拦截处理网络请求
    private Interceptor authorization = chain -> {
        Request request = chain.request();
        //添加header
        Request build = request.newBuilder()
                .header("Authorization", "token")
                .method(request.method(), request.body())
                .build();
        Response proceed = chain.proceed(build);
        ResponseBody responseBody = proceed.body();
        //处理返回结果
        if (responseBody != null && responseBody.contentLength() != 0) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer clone = source.getBuffer().clone();
            String result = clone.readString(Charset.defaultCharset());
            //Do something
            FLog.d("TAG", result);
        }
        return proceed;
    };


}
