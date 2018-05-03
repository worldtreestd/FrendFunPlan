package com.legend.ffpmvp.circle.presenter;

import com.legend.ffpmvp.circle.contract.CreateContract;

import java.io.File;

import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;

/**
 * @author Legend
 * @data by on 2018/2/4.
 * @description
 */

public class CreateCirclePresenter implements CreateContract.ICreatePresenter {

    private CreateContract.ICreateModel model;
    private CreateContract.ICreateView view;

    public CreateCirclePresenter(CreateContract.ICreateModel model,CreateContract.ICreateView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void createExecute(File file, String... params) {
        model.requestCreate(file, new CreateContract.CallBack() {
            @Override
            public void start() {
                view.showLoading();
            }

            @Override
            public void success() {
                view.showError(JOIN_OK);
            }

            @Override
            public void end() {
                view.hideLoading();
            }

            @Override
            public void showError(int code) {
                view.showError(code);
            }
        },params);
    }
}
