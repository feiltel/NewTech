package com.nut2014.baselibrary.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by admin on 2016/1/21.
 */
public class FLog {
    public static String get(Object show) {
        Throwable stack = new Throwable().fillInStackTrace();
        StackTraceElement[] trace = stack.getStackTrace();
        return trace[1].getClassName() + "   " + trace[1].getMethodName() + "   line:" + trace[1].getLineNumber() + " >>> " + show.toString();
    }

    private static String getDay() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format1.format(new Date(System.currentTimeMillis()));
    }

    private static void write2CrashFile(final String msg, String crashPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                File dir = new File(crashPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(crashPath + getDay() + ".txt", true);
                    fileWriter.write(msg);
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
        }).start();


    }

    public static void d(String TAG, String msg) {

        int LOG_MAXLENGTH = 2000;
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.d(TAG + i, showLog(msg.substring(start, end)));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.d(TAG, showLog(msg.substring(start, strLength)));
                break;
            }
        }
    }

    public static void e(String TAG, String msg) {
        int LOG_MAXLENGTH = 2000;
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(TAG + i, showLog(msg.substring(start, end)));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(TAG, showLog(msg.substring(start, strLength)));
                break;
            }
        }
    }


    private static boolean isDebugVersion(Application application) {
        try {
            ApplicationInfo info = application.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String showLog(Object object) {
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        //避免定位到本方法
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("d") == 0) {
                currentIndex = i + 1;
                break;
            }
            if (stackTraceElement[i].getMethodName().compareTo("e") == 0) {
                currentIndex = i + 1;
                break;
            }
        }
        String logInfo = "";
        if (currentIndex >= 0 && currentIndex < stackTraceElement.length) {
            String fullClassName = stackTraceElement[currentIndex].getClassName();
            String className = fullClassName.substring(fullClassName
                    .lastIndexOf(".") + 1);
            String methodName = stackTraceElement[currentIndex].getMethodName();
            String lineNumber = String
                    .valueOf(stackTraceElement[currentIndex].getLineNumber());
            String startLine = "                                            \n";
            String endLine = "\n                                            \n";
            logInfo = startLine + "at " + fullClassName + "." + methodName + "("
                    + className + ".java:" + lineNumber + ")━━>" + methodName + "\n" + object.toString() + endLine;
        } else {
            logInfo = object.toString();
        }
        return logInfo;

    }

    public static String showLogCat(Object object) {
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        //避免定位到本方法
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("showLogCat") == 0) {
                currentIndex = i + 1;
                break;
            }
        }
        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName
                .lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        String lineNumber = String
                .valueOf(stackTraceElement[currentIndex].getLineNumber());
        String startLine = "                                            \n";
        String endLine = "\n                                            \n";
        return startLine + "at " + fullClassName + "." + methodName + "("
                + className + ".java:" + lineNumber + ")━━>" + methodName + "\n" + object.toString() + endLine;


    }
}
