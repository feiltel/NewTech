package com.nut2014.baselibrary.http;


import android.app.Activity;
import android.content.Intent;

import com.nut2014.baselibrary.utils.ActivityManager;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.baselibrary.utils.JsonUtil;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
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
/*
    public interface ApiService {
    @POST("login")
    Call<LoginBean> login(@Query("username") String username,@Query("password") String password );


    @POST("system/json")
    Call<List<ConfigBean>> getConfig();
}

    private Interceptor authorization = chain -> {
        Request request = chain.request();
        //添加header
        Request build = request.newBuilder()
                .header("Authorization", UserDataUtil.getAuthKey())
                .header("X-Requested-With", "XMLHttpRequest")
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
            try {
                BaseResponseBean responseBean = JsonUtil.parseObject(result, BaseResponseBean.class);
                if (responseBean.getCode() == 1) {
                    jump2Login();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            FLog.d("TAG", result);
        }
        return proceed;
    };

    private void jump2Login() {
        Activity currentActivity = ActivityManager.getInstance().getCurrentActivity();
        if (currentActivity != null) {
            currentActivity.startActivity(new Intent(currentActivity, LoginActivity.class));
        }
    }*/

}
