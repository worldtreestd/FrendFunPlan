package com.legend.ffpmvp.circle.contract;

import java.io.File;

/**
 * @author Legend
 * @data by on 2018/2/4.
 * @description 创建圈子
 */

public class CreateContract {

    public interface ICreateModel {
        /**
         * 具体创建
         * @param callBack
         * @param params
         */
        void requestCreate(File file,CallBack callBack, String...params);
    }

    public interface ICreatePresenter {
        /**
         *  执行创建
         * @param params
         */
        void createExecute(File file,String...params);
    }

    public interface ICreateView {

        /**
         *  显示进度
         */
        void showLoading();

        /**
         *  隐藏进度
         */
        void hideLoading();

        /**
         *  错误信息
         * @param code
         */
        void showError(int code);

    }

    public interface CallBack {
        void start();
        void success();
        void end();
        void showError(int code);
    }
}
