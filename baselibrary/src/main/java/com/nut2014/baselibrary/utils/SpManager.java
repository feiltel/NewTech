package com.nut2014.baselibrary.utils;

import android.app.Activity;
import android.content.Context;

/**
 * @author feiltel 2020/4/22 0022
 */
public class SpManager {
    private static final SpManager ourInstance = new SpManager();

    public  static SpManager getInstance() {
        return ourInstance;
    }

    private SpManager() {
    }

    public String getString(Context context, String fileName, String key) {
        return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).getString(key, "");
    }

    public void setString(Context context, String fileName, String key, String value) {
        context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public boolean getBoolean(Context context, String fileName, String key) {
        return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).getBoolean(key, false);
    }

    public void setBoolean(Context context, String fileName, String key, boolean value) {
        context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public int getInt(Context context, String fileName, String key) {
        return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).getInt(key, 0);
    }

    public void setfInt(Context context, String fileName, String key, int value) {
        context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit().putInt(key, value).apply();
    }


    public void remove(Context context, String fileName, String key) {
        context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit().remove(key).apply();
    }

    public void clear(Context context, String fileName) {
        context.getSharedPreferences(fileName, Activity.MODE_PRIVATE).edit().clear().apply();
    }
}
