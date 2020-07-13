package com.nut2014.newtech.workManager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.nut2014.baselibrary.http.OkHttpManager;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.newtech.Constant;

import java.util.concurrent.TimeUnit;

/**
 * @author feiltel 2020/5/7 0007
 */
public class LogUploadWorker extends Worker {
    private static String TAG = "LogWorker";

    public LogUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void runUploadLog(Context context) {


        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.METERED)
                .build();
        //每隔15分钟执行一次  最小间隔为15分钟
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(LogUploadWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        Configuration config = new Configuration.Builder().build();
        WorkManager.initialize(context, config);
        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "LogWorker>>>>>>>>>>>>>>");
        OkHttpManager.getAsyn(Constant.baseUrl + "/user/loginWeb?phone=111&veriCode=11111", new OkHttpManager.ResultCallback() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(String string) {
                FLog.d(TAG, string);
            }
        });
        return Result.success();
    }
}
//Worker 约束
      /*  Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.METERED) // 网络状态 网络连接时执行
                .setRequiresBatteryNotLow(true)                 // 不在电量不足时执行
                 .setRequiresCharging(true)                      // 在充电时执行
                 .setRequiresStorageNotLow(true)                 // 不在存储容量不足时执行
                 .setRequiresDeviceIdle(true)                // 在待机状态下执行，需要 API 23
                .build();*/
//单次执行的Worker
       /* OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(LogWorker.class)
                .setConstraints(constraints)
                .build();*/