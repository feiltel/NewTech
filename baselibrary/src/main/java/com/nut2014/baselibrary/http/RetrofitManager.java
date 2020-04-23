package com.nut2014.baselibrary.http;


import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author feiltel 2020/4/21 0021
 */
public class RetrofitManager {


    private static RetrofitManager sInstance;
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitManager.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance;
    }

    public void init(String baseUrl, Interceptor interceptor) {
        if (mRetrofit == null) {
            //初始化一个OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    .writeTimeout(30000, TimeUnit.MILLISECONDS);
            builder.addInterceptor(interceptor);
            //日志
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            OkHttpClient okHttpClient = builder.build();
            //使用该OkHttpClient创建一个Retrofit对象
            mRetrofit = new Retrofit.Builder()
                    //添加Gson数据格式转换器支持
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加RxJava语言支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //指定网络请求client
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .build();
        }
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            throw new IllegalStateException("Retrofit instance hasn't init!");
        }
        return mRetrofit;
    }

    public static <T> T create(final Class<T> service) {
        return RetrofitManager.getInstance().getRetrofit().create(service);
    }


}
