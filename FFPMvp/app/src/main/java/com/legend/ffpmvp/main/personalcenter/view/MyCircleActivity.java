package com.legend.ffpmvp.main.personalcenter.view;

import android.os.Handler;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.personalcenter.model.MyCircleModelImpl;
import com.legend.ffpmvp.main.personalcenter.presenter.MyCirclePresenter;
import com.legend.ffpmvp.main.presenter.ICommonPresenter;
import com.legend.ffpmvp.main.view.ICommonView;

import java.lang.ref.WeakReference;

import static com.legend.ffpmvp.main.personalcenter.presenter.MyCirclePresenter.adapter;

/**
 * @author Legend
 * @data by on 2018/3/1.
 * @description
 */

public class MyCircleActivity extends BaseActivity implements ICommonView {

    private XRecyclerView mRecyclerView;
    private ICommonPresenter presenter;
    private ICommonModel model;

    @Override
    public Object setResourceLayout() {
        return R.layout.mycircle_layout;
    }

    @Override
    public void initView() {
        mRecyclerView = $(R.id.mRecyclerView);
        model = new MyCircleModelImpl();
        WeakReference<MyCircleActivity> view = new WeakReference<>(this);
        presenter = new MyCirclePresenter(model,view.get());

        refreshData();
    }

    @Override
    public void initListener() {

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
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
    public boolean showHomeAsUp() {
        return true;
    }

    public void refreshData() {
        new Handler().postDelayed(() -> {
            presenter.onRefresh();
            mRecyclerView.refreshComplete();
        },500);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noMoreData() {

    }

    @Override
    public void loadMoreComplete() {

    }
}
