package com.legend.ffpmvp.circle.presenter;

import com.legend.ffpmvp.circle.contract.CircleCommonContract;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/31.
 * @description
 */

public class CircleContentPresenter implements CircleCommonContract.ICircleContentPresenter {

    private CircleCommonContract.ICircleContentModel model;
    private CircleCommonContract.ICircleContentView view;

    public CircleContentPresenter(CircleCommonContract.ICircleContentModel model, CircleCommonContract.ICircleContentView view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void getData(int id) {
        model.getData(id, new CircleCommonContract.RequestCallBack() {
            @Override
            public void onSuccess(List list) {

            }

            @Override
            public void onFailure() {
                view.showResult();
            }
        });
    }
}
