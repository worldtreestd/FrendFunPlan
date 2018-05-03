package com.legend.ffpmvp.circle.view;

import android.content.Intent;
import android.os.Handler;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.contract.CircleCommonContract;
import com.legend.ffpmvp.circle.model.CirclePlanModelImpl;
import com.legend.ffpmvp.circle.presenter.CirclePlanPresenter;
import com.legend.ffpmvp.common.view.BaseFragment;

import java.lang.ref.WeakReference;

import static com.legend.ffpmvp.circle.presenter.CirclePlanPresenter.adapter;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class CirclePlanView extends BaseFragment implements CircleCommonContract.ICircleContentView {

    private CircleCommonContract.ICircleContentModel model;
    private CircleCommonContract.ICircleContentPresenter presenter;
    private XRecyclerView mRecyclerView;
    private int circle_id;

    @Override
    public int setResourceLayoutId() {
        return R.layout.circle_plan_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }

    @Override
    public void initView() {
        model = new CirclePlanModelImpl();
        WeakReference<CirclePlanView> reference = new WeakReference<>(this);
        presenter = new CirclePlanPresenter(model,reference.get());
        Intent intent = getActivity().getIntent();
        circle_id = intent.getIntExtra(CircleContentView.CIRCLE_ID, 0);
    }

    @Override
    public void initListener() {
        mRecyclerView = getmRecyclerView();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public void refreshData() {
        new Handler().postDelayed(() -> {
            presenter.getData(circle_id);
            mRecyclerView.refreshComplete();
        },500);
    }

    @Override
    public void showResult() {

    }
}
