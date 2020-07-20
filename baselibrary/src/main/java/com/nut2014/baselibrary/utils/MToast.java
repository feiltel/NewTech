package com.nut2014.baselibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * @author feiltel 2020/4/8 0008
 */
public class MToast {
    public static void show(Context context, String msg) {
        Toasty.normal(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void success(Context context, String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void error(Context context, String text) {
        Toasty.error(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(Context context, String text) {
        Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void warning(Context context, String text) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void normal(Context context, String text, Drawable icon) {
        Toasty.normal(context, text, icon).show();
    }
}
