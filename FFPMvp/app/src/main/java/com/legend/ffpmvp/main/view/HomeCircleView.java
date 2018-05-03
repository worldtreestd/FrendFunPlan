package com.legend.ffpmvp.main.view;

import android.app.Dialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseFragment;
import com.legend.ffpmvp.common.view.XRecyclerViewDivider;
import com.legend.ffpmvp.common.view.banner.BannerView;
import com.legend.ffpmvp.main.model.HomeCircleModelImpl;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.presenter.HomeCirclePresenter;
import com.legend.ffpmvp.main.presenter.ICommonPresenter;

import java.lang.ref.WeakReference;

import static com.legend.ffpmvp.main.presenter.HomeCirclePresenter.adapter;
import static com.legend.ffpmvp.main.presenter.HomeCirclePresenter.bannerAdapter;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class HomeCircleView extends BaseFragment implements ICommonView {

    private Dialog dialog;
    private XRecyclerView mRecyclerView;
    private View mView;
    private ICommonPresenter presenter;
    private ICommonModel model;
    private BannerView mBannerView;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    public int setResourceLayoutId() {
        return R.layout.all_circle_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }

    @Override
    public void initView() {
        mView = getmView();
        model = new HomeCircleModelImpl();
        WeakReference<HomeCircleView> reference = new WeakReference<>(this);
        presenter = new HomeCirclePresenter(model,reference.get());
        mBannerView = (BannerView) LayoutInflater.from(mView.getContext())
                .inflate(R.layout.banner_view_layout,null);
        mSwipeRefresh = $(R.id.mSwipeRefresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void initListener() {
        mRecyclerView = getmRecyclerView();
        mRecyclerView.addHeaderView(mBannerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new XRecyclerViewDivider(mView.getContext(),XRecyclerViewDivider.STAGGERED,"0"));
        mRecyclerView.setPullRefreshEnabled(false);
        mSwipeRefresh.setOnRefreshListener(() -> {
            mSwipeRefresh.setRefreshing(true);
            refreshData();
        });
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    @Override
    public void refreshData() {
        mRecyclerView.setNoMore(false);
        presenter.onRefresh();
        mBannerView.cancelScroll();
        new Handler().postDelayed(() -> {
            if (bannerAdapter.getSize() > 0) {
                mBannerView.setData(bannerAdapter);
            }
            mSwipeRefresh.setRefreshing(false);
        },600);
    }

    private void loadMoreData() {
        presenter.onLoadMore();
    }

    @Override
    public void showLoading() {
        dialog = ToastUtils.createLoadingDialog(mView.getContext(),"正在努力...加载中");
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
