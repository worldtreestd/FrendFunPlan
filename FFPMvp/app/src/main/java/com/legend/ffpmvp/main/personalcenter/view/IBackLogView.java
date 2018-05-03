package com.legend.ffpmvp.main.personalcenter.view;

import com.legend.ffpmvp.main.view.ICommonView;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface IBackLogView extends ICommonView {

    /**
     *  根据状态码进行相应操作
     * @param code
     */
    void operateResult(int code);
}
