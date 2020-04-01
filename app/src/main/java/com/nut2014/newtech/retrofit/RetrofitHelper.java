package com.nut2014.newtech.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取RetrofitService实例
 * Created by yangle on 2017/6/27.
 */

public class RetrofitHelper {
    //private static final String SERVER_URL = "http://nutstudio.net:8083/diary/";
    public static final String SERVER_URL = "http://192.168.31.196:8087/newtech/";
    private static RetrofitHelper retrofitHelper;
    private Retrofit retrofit;


    public static RetrofitHelper getInstance() {
        return retrofitHelper == null ? retrofitHelper = new RetrofitHelper() : retrofitHelper;
    }


    private RetrofitHelper() {


        //
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//设置日志Level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//添加拦截器到OkHttp 会影响上传速度
        httpClient.addInterceptor(logging);


        // 初始化Retrofit
         retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) // json解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .client(httpClient.build())
                .build();




    }

    public <T> T getApiService(Class<T> apiServer) {
        return retrofit.create(apiServer);
    }

}
