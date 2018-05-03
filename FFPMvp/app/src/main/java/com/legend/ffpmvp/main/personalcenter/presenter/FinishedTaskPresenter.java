package com.legend.ffpmvp.main.personalcenter.presenter;

import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.personalcenter.model.FinishedTaskAdapter;
import com.legend.ffpmvp.main.presenter.ICommonPresenter;
import com.legend.ffpmvp.main.view.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class FinishedTaskPresenter implements ICommonPresenter {

    public static FinishedTaskAdapter adapter;
    private ICommonModel planModel;
    private ICommonView planView;
    private List<HomePlanBean> planBeanLists;

    public FinishedTaskPresenter(ICommonModel planModel,ICommonView planView) {
        this.planModel = planModel;
        this.planView = planView;
        planBeanLists = new ArrayList<>();
        adapter = new FinishedTaskAdapter(planBeanLists);
    }
    @Override
    public void onRefresh() {
        planModel.getData(0, new ICommonModel.RequestResult() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List beanList) {
                planBeanLists.clear();
                planBeanLists.addAll(beanList);
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
