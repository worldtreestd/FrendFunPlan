package com.legend.ffpmvp.main.personalcenter.presenter;

import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.adapter.CircleListAdapter;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.presenter.ICommonPresenter;
import com.legend.ffpmvp.main.view.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class MyCirclePresenter implements ICommonPresenter {

    public static CircleListAdapter adapter;
    private ICommonModel circleModel;
    private ICommonView circleView;
    private List<HomeCircleBean> circleBeanLists;

    public MyCirclePresenter(ICommonModel circleModel, ICommonView circleView) {
        this.circleModel = circleModel;
        this.circleView = circleView;
        this.circleBeanLists = new ArrayList<>();
        adapter = new CircleListAdapter(circleBeanLists,0);
    }
    @Override
    public void onRefresh() {
        circleModel.getData(0, new ICommonModel.RequestResult() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List beanList) {
                circleBeanLists.clear();
                circleBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLoadMore() {
    }
}
