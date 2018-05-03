package com.legend.ffpmvp.common.utils;

/**
 * @author Legend
 * @data by on 2018/1/2.
 * @description 后台接口工具类
 */

public class ApiUtils {

    public static final String BASE_URL = "http://api.cspojie.cn";

    /**
     *  圈子列表接口
     */
    public static final String CIRCLES = BASE_URL+"/circles/";

    /**
     *  计划列表接口
     */
    public static final String PLANS = BASE_URL+"/plans/";

    /**
     *  加入圈子
     */
    public static final String PARTCIRCLE = BASE_URL+"/partcircles/";

    /**
     *  发布计划
     */
    public static final String PARTPLANS = BASE_URL+"/partplans/";

    /**
     *  消息
     */
    public static final String MESSAGES = BASE_URL+"/messages/";
    /**
     *  用户
     */
    public static final String USERS = BASE_URL+"/users/";
    /**
     * 获取jwt
     */
    public static final String LOGIN = BASE_URL+"/login/";

    public static final String SEARCH = CIRCLES +"/?search=";

    /**
     *  首页横幅
     */
    public static final String CIRCLE_BANNER = BASE_URL+"/banners/";

    public static final String KEY_WORDS = BASE_URL+"/keywords/";

}
