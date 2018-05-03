package com.legend.ffpmvp.main.presenter;

import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.common.adapter.PlanListAdapter;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.view.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class HomePlanPresenter implements ICommonPresenter {

    public static PlanListAdapter adapter;
    private ICommonModel planModel;
    private ICommonView planView;
    private int mCurrentPageIndex = 1;
    private List<HomePlanBean> planBeanLists;

    public HomePlanPresenter(ICommonModel planModel, ICommonView planView) {
        this.planModel = planModel;
        this.planView = planView;
        this.planBeanLists = new ArrayList<>();
        adapter = new PlanListAdapter(planBeanLists);
    }

    @Override
    public void onRefresh() {
        planBeanLists.clear();
        mCurrentPageIndex = 1;
        planModel.getData(mCurrentPageIndex, new ICommonModel.RequestResult() {
            @Override
            public void onStart() {
                planView.showLoading();
            }

            @Override
            public void onSuccess(List beanList) {
                planBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                adapter.notifyDataSetChanged();
                planView.hideLoading();
            }
        });
    }

    @Override
    public void onLoadMore() {
        planModel.getData(++mCurrentPageIndex, new ICommonModel.RequestResult() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List beanList) {
                planBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() >= planModel.count()) {
                    planView.noMoreData();
                } else {
                    planView.loadMoreComplete();
                }
            }
        });
    }
}
