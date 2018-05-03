package com.legend.ffpmvp.main.model;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public interface ICommonModel {

    /**
     * 传入List和当前页数
     * @param
     * @param pageIndex
     */
    void getData(int pageIndex,RequestResult result);

    /**
     * 首页横幅
     * @param result
     */
    void getCircleBanner(RequestResult result);

    /**
     *  记录数量
     * @return
     */
    int count();

    /**
     *  请求接口的回调
     * @param <T>
     */
    public interface RequestResult<T> {
        void onStart();
        void onSuccess(List<T> beanList);
        void onFailure();
        void onEnd();
    }
}
