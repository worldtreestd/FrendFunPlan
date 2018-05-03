package com.legend.ffpmvp.circle.search.presenter;

import android.text.TextUtils;

import com.legend.ffpmvp.circle.search.ISearchContract;
import com.legend.ffpmvp.circle.search.model.KeyWordBean;
import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.adapter.CircleListAdapter;
import com.legend.ffpmvp.main.model.ICommonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class SearchPresenter implements ISearchContract.ISearchPresenter {

    public static CircleListAdapter adapter;
    private ISearchContract.ISearchModel model;
    private ISearchContract.ISearchView view;
    private List<HomeCircleBean> circleBeanLists;
    public static List<KeyWordBean> keyWordBeanLists;

    public SearchPresenter(ISearchContract.ISearchModel model, ISearchContract.ISearchView view) {
        this.model = model;
        this.view = view;
        circleBeanLists = new ArrayList<>();
        keyWordBeanLists = new ArrayList<>();
        adapter = new CircleListAdapter(circleBeanLists);
    }

    @Override
    public void onSearch(String keyword) {
        circleBeanLists.clear();
        keyWordBeanLists.clear();
        if (!TextUtils.isEmpty(keyword)) {
            model.getSearchResult(keyword, new ICommonModel.RequestResult() {
                @Override
                public void onStart() {
                    view.showLoading();
                }

                @Override
                public void onSuccess(List beanList) {
                    circleBeanLists.addAll(beanList);
                }

                @Override
                public void onFailure() {
                    view.showNoResult();
                }

                @Override
                public void onEnd() {
                    adapter.notifyDataSetChanged();
                    view.hideLoading();
                }
            });
        }

        model.requestKeyWord(keyword, new ICommonModel.RequestResult() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List beanList) {
                keyWordBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                view.showKeyWord();
            }
        });
    }
}
