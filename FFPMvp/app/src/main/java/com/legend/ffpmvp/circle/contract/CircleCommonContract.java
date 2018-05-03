package com.legend.ffpmvp.circle.contract;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface CircleCommonContract {

    interface ICircleContentModel {

        void getData(int id,RequestCallBack callBack);
    }

    interface ICircleContentPresenter {
        void getData(int id);
    }

    interface ICircleContentView {
        void showResult();
    }

    interface RequestCallBack<T> {

        void onSuccess(List<T> tList);

        void onFailure();

    }
}
