package com.legend.ffpmvp.main.view;

import android.app.Dialog;
import android.os.Handler;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseFragment;
import com.legend.ffpmvp.main.model.HomePlanModelImpl;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.presenter.HomePlanPresenter;
import com.legend.ffpmvp.main.presenter.ICommonPresenter;

import java.lang.ref.WeakReference;

import static com.legend.ffpmvp.main.presenter.HomePlanPresenter.adapter;


/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 全部计划列表页面
 */

public class HomePlanView extends BaseFragment implements ICommonView {

    private Dialog dialog;
    private ICommonPresenter presenter;
    private ICommonModel model;
    private View mView;
    private XRecyclerView mRecyclerView;

    @Override
    public int setResourceLayoutId() {
        return R.layout.all_plan_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }

    @Override
    public void initView() {
        mView = getmView();
        model = new HomePlanModelImpl();
        WeakReference<HomePlanView> view = new WeakReference<>(this);
        presenter = new HomePlanPresenter(model,view.get());
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
                loadMoreData();
            }
        });
    }

    @Override
    public void refreshData() {
        new Handler().postDelayed(() -> {
            presenter.onRefresh();
            mRecyclerView.refreshComplete();
        },500);
    }

    private void loadMoreData() {
        presenter.onLoadMore();
    }
    @Override
    public void showLoading() {
        dialog = ToastUtils.createLoadingDialog(mView.getContext(),"正在努力...加载中~");
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void noMoreData() {
        mRecyclerView.setNoMore(true);
    }

    @Override
    public void loadMoreComplete() {
        mRecyclerView.loadMoreComplete();
    }
}
