package com.legend.ffpmvp.main.personalcenter.view;

import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.personalcenter.model.FinishedTaskModelImpl;
import com.legend.ffpmvp.main.personalcenter.presenter.FinishedTaskPresenter;
import com.legend.ffpmvp.main.presenter.ICommonPresenter;
import com.legend.ffpmvp.main.view.ICommonView;

import java.lang.ref.WeakReference;

import static com.legend.ffpmvp.main.personalcenter.presenter.FinishedTaskPresenter.adapter;

/**
 * @author Legend
 * @data by on 2018/3/1.
 * @description
 */

public class FinishedTaskActivity extends BaseActivity implements ICommonView {

    private XRecyclerView mRecyclerView;
    private ICommonModel model;
    private ICommonPresenter presenter;
    private AppCompatImageView mBackground;

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
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public Object setResourceLayout() {
        return R.layout.finishedtask_layout;
    }

    @Override
    public void initView() {
        model = new FinishedTaskModelImpl();
        WeakReference<FinishedTaskActivity> view = new WeakReference<>(this);
        presenter = new FinishedTaskPresenter(model,view.get());
        mRecyclerView = $(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mBackground = $(R.id.imageview);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(MyApplication.getInstance()).
                        load("https://api.dujin.org/bing/1920.php")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .into(mBackground);
            }
        });
        refreshData();
    }

    @Override
    public void initListener() {
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
}
