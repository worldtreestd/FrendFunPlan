package com.legend.ffpmvp.plan.model;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface IPlanContentModel {

    /**
     *  第二个参数判断请求类型
     * @param id
     * @param status
     * @param result
     */
    void partPlan(int id,int status,RequestResult result);

    public interface RequestResult {

        void onSuccess();

        void onFailure(int code);
    }
}
