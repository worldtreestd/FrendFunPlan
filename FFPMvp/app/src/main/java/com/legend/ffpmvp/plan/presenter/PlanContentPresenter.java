package com.legend.ffpmvp.plan.presenter;

import com.legend.ffpmvp.plan.model.IPlanContentModel;
import com.legend.ffpmvp.plan.view.IPlanContentView;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class PlanContentPresenter implements IPlanContentPresenter {

    private IPlanContentModel model;
    private IPlanContentView view;

    public PlanContentPresenter(IPlanContentModel model,IPlanContentView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void partPlan(int id,int status) {
        model.partPlan(id,status, new IPlanContentModel.RequestResult() {
            @Override
            public void onSuccess() {
                view.updateUi();
            }

            @Override
            public void onFailure(int code) {
                view.showErrorMsg(code);
            }
        });
    }
}
