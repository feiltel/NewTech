package com.nut2014.newtech;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.nut2014.baselibrary.base.BaseApplication;
import com.nut2014.baselibrary.http.RetrofitManager;
import com.nut2014.baselibrary.networklibrary.NetWorkManager;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.newtech.workManager.LogWorker;
import com.wanjian.cockroach.Cockroach;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

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
        Cockroach.init(this, Constant.crashPath, null);
        //网络状态监听类注册
        NetWorkManager.getDefault().init(this);
        // Retrofit 初始化
        final String BASE_URL = "http://x501.10015678.com/";
        RetrofitManager.getInstance().init(BASE_URL, authorization);

        //Worker
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.METERED) // 网络状态 网络连接时执行
                /* .setRequiresBatteryNotLow(true)                 // 不在电量不足时执行
                 .setRequiresCharging(true)                      // 在充电时执行
                 .setRequiresStorageNotLow(true)                 // 不在存储容量不足时执行
                 .setRequiresDeviceIdle(true)      */              // 在待机状态下执行，需要 API 23
                .build();
         //单次执行
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(LogWorker.class)
                .setConstraints(constraints)
                .build();
        //每隔15分钟执行一次  最小间隔为15分钟
        PeriodicWorkRequest periodicWorkRequest=  new PeriodicWorkRequest
                .Builder(LogWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
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
