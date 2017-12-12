package com.legend.ffplan.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Legend
 * @data by on 2017/12/5.
 * @description 时间日期工具类
 */

public class DateUtils {

    /**
     *  当前时间
     */
    public static String getDate() {
//        // 之前版本
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String now_date = dateFormat.format(date);
        // java8写法
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDate localDate = LocalDate.now();
//        String now_date = localDate.format(dateTimeFormatter);
        return now_date;
    }

}
