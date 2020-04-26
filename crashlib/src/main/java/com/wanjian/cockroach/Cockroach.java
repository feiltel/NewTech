package com.wanjian.cockroach;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.wanjian.cockroach.compat.ActivityKillerV15_V20;
import com.wanjian.cockroach.compat.ActivityKillerV21_V23;
import com.wanjian.cockroach.compat.ActivityKillerV24_V25;
import com.wanjian.cockroach.compat.ActivityKillerV26;
import com.wanjian.cockroach.compat.ActivityKillerV28;
import com.wanjian.cockroach.compat.IActivityKiller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.weishu.reflection.Reflection;

/**
 * Created by wanjian on 2017/2/14.
 */

public final class Cockroach {

    private static IActivityKiller sActivityKiller;
    private static ExceptionHandler sExceptionHandler;
    private static boolean sInstalled = false;//标记位，避免重复安装卸载
    private static boolean sIsSafeMode;

    private Cockroach() {
    }

    private static String getNow() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format1.format(new Date(System.currentTimeMillis()));
    }

    private static String getDay() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format1.format(new Date(System.currentTimeMillis()));
    }

    private static String getCrashContent(Throwable ex, Map<String, String> mapS) {
        StringBuilder sb = new StringBuilder();
        if (mapS != null) {
            for (Map.Entry<String, String> entry : mapS.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        System.out.print(sb.toString());
        return sb.toString();
    }

    private static void saveCrashInfo2File(Throwable ex, String logPath, Map<String, String> mapS) {
        String fileName = getDay() + ".txt";
        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //在文件头部加入设备信息
        File logFile = new File(logPath + fileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapS.putAll(getDevStr());
        }

        FileWriter fileWriter = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            fileWriter = new FileWriter(logPath + fileName, true);
            fileWriter.write(getCrashContent(ex, mapS));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static Map<String, String> getLogMap(Context ctx) {
        Map<String, String> map = new HashMap<>();
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi != null) {
            String versionName = pi.versionName == null ? "null" : pi.versionName;
            String versionCode = pi.versionCode + "";
            map.put("Time", getNow());
            map.put("versionCode", versionCode);
            map.put("versionName", versionName);
        }


        return map;
    }

    private static Map<String, String> getDevStr() {
        Map<String, String> map = new HashMap<>();
        map.put("品牌： ", Build.MANUFACTURER);
        map.put("型号： ", Build.PRODUCT);
        map.put("SDK_INT", Build.VERSION.SDK_INT + "");

        map.put("版本： ", Build.MODEL);
        map.put("主板： ", Build.BOARD);
        map.put("系统启动程序版本号： ", Build.BOOTLOADER);
        map.put("系统定制商： ", Build.BRAND);
        map.put("cpu指令集： ", Build.CPU_ABI);
        map.put("cpu指令集2 ", Build.CPU_ABI2);
        map.put("设置参数： ", Build.DEVICE);
        map.put("显示屏参数：", Build.DISPLAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            map.put("无线电固件版本：", Build.getRadioVersion() + "");
        }
        map.put("硬件识别码： ", Build.FINGERPRINT);
        map.put("硬件名称： ", Build.HARDWARE);
        map.put("HOST: ", Build.HOST);
        map.put("Build.ID);", Build.ID);
        map.put("硬件序列号： ", Build.SERIAL);
        map.put("描述Build的标签： ", Build.TAGS);
        map.put("TIME: ", Build.TIME + "");
        map.put("builder类型", Build.TYPE);
        map.put("USER: ", Build.USER);
        return map;

    }

    /**
     * 默认一天存储一个文件
     *
     * @param context      上下文
     * @param path         存储路径 传入为空时 默认路径为 /crash/
     * @param jumpActivity 出现严重错误是跳转的 Activity
     */
    public static void init(final Context context, String path, final Class<?> jumpActivity) {
        if (path == null || path.trim().length() < 1) {
            path = Environment.getExternalStorageDirectory().getPath() + "/crash/";
        }
        final String nowPath = path;
        install(context, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                Log.d("Cockroach", "onUncaughtExceptionHappened");
                saveCrashInfo2File(throwable, nowPath, getLogMap(context));
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                Log.d("Cockroach", "onBandageExceptionHappened");

                saveCrashInfo2File(throwable, nowPath, getLogMap(context));
                if (jumpActivity != null) {
                    Intent intent = new Intent(context, jumpActivity);
                    intent.putExtra("iscrash", true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }

            @Override
            protected void onEnterSafeMode() {
                Log.d("Cockroach", "onEnterSafeMode");
            }

            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                super.onMayBeBlackScreen(e);
                Log.d("Cockroach", "onMayBeBlackScreen");
                saveCrashInfo2File(e, nowPath, getLogMap(context));
               /* new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(context, "程序出现问题，请到用户反馈模块反馈,谢谢。", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
*/

                //黑屏时建议直接杀死app
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    private static void install(Context ctx, ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        try {
            //解除 android P 反射限制
            Reflection.unseal(ctx);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

        initActivityKiller();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler != null) {
                    sExceptionHandler.uncaughtExceptionHappened(t, e);
                }
                if (t == Looper.getMainLooper().getThread()) {
                    isChoreographerException(e);
                    safeMode();
                }
            }
        });

    }

    /**
     * 替换ActivityThread.mH.mCallback，实现拦截Activity生命周期，直接忽略生命周期的异常的话会导致黑屏，目前
     * 会调用ActivityManager的finishActivity结束掉生命周期抛出异常的Activity
     */
    private static void initActivityKiller() {
        //各版本android的ActivityManager获取方式，finishActivity的参数，token(binder对象)的获取不一样
        if (Build.VERSION.SDK_INT >= 28) {
            sActivityKiller = new ActivityKillerV28();
        } else if (Build.VERSION.SDK_INT >= 26) {
            sActivityKiller = new ActivityKillerV26();
        } else if (Build.VERSION.SDK_INT == 25 || Build.VERSION.SDK_INT == 24) {
            sActivityKiller = new ActivityKillerV24_V25();
        } else if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 23) {
            sActivityKiller = new ActivityKillerV21_V23();
        } else if (Build.VERSION.SDK_INT >= 15 && Build.VERSION.SDK_INT <= 20) {
            sActivityKiller = new ActivityKillerV15_V20();
        } else if (Build.VERSION.SDK_INT < 15) {
            sActivityKiller = new ActivityKillerV15_V20();
        }

        try {
            hookmH();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void hookmH() throws Exception {

        final int LAUNCH_ACTIVITY = 100;
        final int PAUSE_ACTIVITY = 101;
        final int PAUSE_ACTIVITY_FINISHING = 102;
        final int STOP_ACTIVITY_HIDE = 104;
        final int RESUME_ACTIVITY = 107;
        final int DESTROY_ACTIVITY = 109;
        final int NEW_INTENT = 112;
        final int RELAUNCH_ACTIVITY = 126;
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getDeclaredMethod("currentActivityThread").invoke(null);

        Field mhField = activityThreadClass.getDeclaredField("mH");
        mhField.setAccessible(true);
        final Handler mhHandler = (Handler) mhField.get(activityThread);
        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        callbackField.set(mhHandler, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (Build.VERSION.SDK_INT >= 28) {//android P 生命周期全部走这
                    final int EXECUTE_TRANSACTION = 159;
                    if (msg.what == EXECUTE_TRANSACTION) {
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            sActivityKiller.finishLaunchActivity(msg);
                            notifyException(throwable);
                        }
                        return true;
                    }
                    return false;
                }
                switch (msg.what) {
                    case LAUNCH_ACTIVITY:// startActivity--> activity.attach  activity.onCreate  r.activity!=null  activity.onStart  activity.onResume
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            sActivityKiller.finishLaunchActivity(msg);
                            notifyException(throwable);
                        }
                        return true;
                    case RESUME_ACTIVITY://回到activity onRestart onStart onResume
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            sActivityKiller.finishResumeActivity(msg);
                            notifyException(throwable);
                        }
                        return true;
                    case PAUSE_ACTIVITY_FINISHING://按返回键 onPause
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            sActivityKiller.finishPauseActivity(msg);
                            notifyException(throwable);
                        }
                        return true;
                    case PAUSE_ACTIVITY://开启新页面时，旧页面执行 activity.onPause
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            sActivityKiller.finishPauseActivity(msg);
                            notifyException(throwable);
                        }
                        return true;
                    case STOP_ACTIVITY_HIDE://开启新页面时，旧页面执行 activity.onStop
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            sActivityKiller.finishStopActivity(msg);
                            notifyException(throwable);
                        }
                        return true;
                    case DESTROY_ACTIVITY:// 关闭activity onStop  onDestroy
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            notifyException(throwable);
                        }
                        return true;
                }
                return false;
            }
        });
    }


    private static void notifyException(Throwable throwable) {
        if (sExceptionHandler == null) {
            return;
        }
        if (isSafeMode()) {
            sExceptionHandler.bandageExceptionHappened(throwable);
        } else {
            sExceptionHandler.uncaughtExceptionHappened(Looper.getMainLooper().getThread(), throwable);
            safeMode();
        }
    }

    public static boolean isSafeMode() {
        return sIsSafeMode;
    }

    private static void safeMode() {
        sIsSafeMode = true;
        if (sExceptionHandler != null) {
            sExceptionHandler.enterSafeMode();
        }
        while (true) {
            try {
                Looper.loop();
            } catch (Throwable e) {
                isChoreographerException(e);
                if (sExceptionHandler != null) {
                    sExceptionHandler.bandageExceptionHappened(e);
                }
            }
        }
    }

    /**
     * view measure layout draw时抛出异常会导致Choreographer挂掉
     * <p>
     * 建议直接杀死app。以后的版本会只关闭黑屏的Activity
     *
     * @param e
     */
    private static void isChoreographerException(Throwable e) {
        if (e == null || sExceptionHandler == null) {
            return;
        }
        StackTraceElement[] elements = e.getStackTrace();
        if (elements == null) {
            return;
        }

        for (int i = elements.length - 1; i > -1; i--) {
            if (elements.length - i > 20) {
                return;
            }
            StackTraceElement element = elements[i];
            if ("android.view.Choreographer".equals(element.getClassName())
                    && "Choreographer.java".equals(element.getFileName())
                    && "doFrame".equals(element.getMethodName())) {
                sExceptionHandler.mayBeBlackScreen(e);
                return;
            }

        }
    }


}
