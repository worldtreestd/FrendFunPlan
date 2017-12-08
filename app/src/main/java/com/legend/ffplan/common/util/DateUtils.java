package com.legend.ffplan.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Legend
 * @data by on 2017/12/5.
 * @description 时间工具类
 */

public class DateUtils {

    /**
     *  当前时间
     */
    public static String getDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/DD");
        String now_time = dateFormat.format(date);
        return now_time;
    }
}
