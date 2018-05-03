package com.legend.ffpmvp.main.view;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public interface ICommonView {

    /**
     *  显示进度
     */
    void showLoading();

    /**
     *  隐藏进度
     */
    void hideLoading();

    /**
     *  数据加载完成
     */
    void noMoreData();

    /**
     *  加载更多完成
     */
    void loadMoreComplete();
}
