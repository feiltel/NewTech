package com.nut2014.newtech.workManager;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.nut2014.baselibrary.utils.MToast;
import com.nut2014.newtech.MyApp;

/**
 * @author feiltel 2020/5/7 0007
 */
public class LogWorker extends Worker {
    public LogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("TAG","LogWorker>>>>>>>>>>>>>>");

        /*Looper.prepare();
        MToast.show(getApplicationContext(),"LogWorker");
        Looper.loop();*/
        return Result.success();
    }
}
