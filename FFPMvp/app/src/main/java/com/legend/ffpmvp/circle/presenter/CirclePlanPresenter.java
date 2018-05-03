package com.legend.ffpmvp.circle.presenter;

import com.legend.ffpmvp.circle.contract.CircleCommonContract;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.common.adapter.PlanListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class CirclePlanPresenter implements CircleCommonContract.ICircleContentPresenter {

    public static PlanListAdapter adapter;
    private CircleCommonContract.ICircleContentModel model;
    private CircleCommonContract.ICircleContentView view;
    private List<HomePlanBean> planBeanLists;


    public CirclePlanPresenter(CircleCommonContract.ICircleContentModel model, CircleCommonContract.ICircleContentView view) {
        this.model = model;
        this.view = view;
        planBeanLists = new ArrayList<>();
        adapter = new PlanListAdapter(planBeanLists);
    }
    @Override
    public void getData(int id) {
        model.getData(id, new CircleCommonContract.RequestCallBack() {
            @Override
            public void onSuccess(List list) {
                planBeanLists.clear();
                planBeanLists.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
