package com.legend.ffpmvp.circle.search;

import com.legend.ffpmvp.main.model.ICommonModel;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public interface ISearchContract {

    public interface ISearchModel {
        /**
         *  搜索传入关键字和搜索完成后的回调
         * @param keyword
         * @param result
         */
        void getSearchResult(String keyword, ICommonModel.RequestResult result);

        /**
         *  传入关键字和结果的回调
         * @param keyword
         * @param result
         */
        void requestKeyWord(String keyword,ICommonModel.RequestResult result);
    }

    public interface ISearchPresenter {
        void onSearch(String keyword);
    }

    public interface ISearchView {

        void showLoading();

        void showNoResult();

        void showKeyWord();

        void hideLoading();
    }

}
