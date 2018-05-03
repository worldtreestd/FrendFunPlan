package com.legend.ffplan.common.util;

/**
 * @author Legend
 * @data by on 2018/1/2.
 * @description 后台接口类
 */

public class ApiUtils {

    public static String BASE_URL = "http://api.cspojie.cn";

    /**
     *  圈子列表接口
     */
    public static String CIRCLES = BASE_URL+"/circles/";

    /**
     *  计划列表接口
     */
    public static String PLANS = BASE_URL+"/plans/";

    /**
     *  加入圈子
     */
    public static String PARTCIRCLE = BASE_URL+"/partcircles/";

    /**
     *  发布计划
     */
    public static String PARTPLANS = BASE_URL+"/partplans/";

    /**
     *  消息
     */
    public static String MESSAGES = BASE_URL+"/messages/";
    /**
     *  用户
     */
    public static String USERS = BASE_URL+"/users/";
    /**
     * 获取jwt
     */
    public static String LOGIN = BASE_URL+"/login/";

    public static String SEARCH = CIRCLES +"/?search=";
}
