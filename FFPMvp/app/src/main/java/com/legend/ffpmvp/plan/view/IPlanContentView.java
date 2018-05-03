package com.legend.ffpmvp.plan.view;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface IPlanContentView {

    /**
     *  更新Ui
     */
    void updateUi();
    /**
     *  错误信息
     * @param code
     */
    void showErrorMsg(int code);

}
