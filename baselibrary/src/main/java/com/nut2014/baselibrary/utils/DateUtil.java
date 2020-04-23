package com.nut2014.baselibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/2/3.
 */
public class DateUtil {
    public static String getDateSimple() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }

    public static String getDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(new Date(System.currentTimeMillis()));
        return date1;
    }

}
