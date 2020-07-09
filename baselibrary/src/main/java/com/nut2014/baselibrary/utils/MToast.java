package com.nut2014.baselibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author feiltel 2020/4/8 0008
 */
public class MToast {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
