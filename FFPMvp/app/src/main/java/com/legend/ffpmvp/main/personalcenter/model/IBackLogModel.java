package com.legend.ffpmvp.main.personalcenter.model;

import android.os.Handler;

import com.legend.ffpmvp.main.model.ICommonModel;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface IBackLogModel extends ICommonModel {

    /**
     *  删除计划
     */
    void deletePlan(int position);

    /**
     *  更新计划
     */
    void updatePlan(int position,int id);

    void setHandler(Handler handler);
}
