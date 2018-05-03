package com.legend.ffpmvp.common.bean;

/**
 * @author Legend
 * @data by on 2018/2/3.
 * @description 状态码
 */

public class Status {
    /**
     *  已经加入
     */
    public static final int ALREADY_JOIN = 400;
    /**
     *  未登录
     */
    public static final int UN_LOGIN = 401;

    /**
     *  删除成功
     */
    public static final int DELETE_OK = 204;
    /**
     *  更新成功
     */
    public static final int UPDATE_OK = 200;

    /**
     *
     */
    public static final int JOIN_OK = 201;
    /**
     *  已经删除/不存在页面
     */
    public static final int NO_EXIT = 404;
    /**
     *  没有权限
     */
    public static final int NO_PERMISSION = 403;
    /**
     *  未知错误
     */
    public static final int UNKNOW_ERROR = 100001;
}
