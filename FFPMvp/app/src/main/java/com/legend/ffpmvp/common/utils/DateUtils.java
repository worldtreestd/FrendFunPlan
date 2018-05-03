package com.legend.ffpmvp.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String now_date = dateFormat.format(date);
        // java8写法
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDate localDate = LocalDate.now();
//        String now_date = localDate.format(dateTimeFormatter);
        return now_date;
    }

    /**
     *  生成随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i < length;i++) {
            int number = random.nextInt(61);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }
}
