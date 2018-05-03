package com.legend.ffpmvp.main.personalcenter.presenter;

import com.legend.ffpmvp.main.presenter.ICommonPresenter;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface IBackLogPresenter extends ICommonPresenter {

    /**
     *  删除计划
     */
    void deletePlan(int position);

    /**
     *  更新计划
     */
    void updatePlan(int position,int id);

    /**
     *  待办事项的数量
     * @return
     */
    int getListSize();
}
