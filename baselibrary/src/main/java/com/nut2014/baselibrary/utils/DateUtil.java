package com.nut2014.baselibrary.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式化样式
     * @return 返回内容
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(date);
    }

    public static void datePick(@NonNull FragmentManager manager, String tag, String pattern, DatePickCallBack callBack) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (view1, year, monthOfYear, dayOfMonth) -> {
                    if (callBack != null) {
                        callBack.picked(DateUtil.format(new Date(year, monthOfYear, dayOfMonth), pattern));
                    }
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(manager, tag);
    }

    public interface DatePickCallBack {
        void picked(String date);
    }

}
