package com.legend.ffpmvp.main.personalcenter.view;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.main.personalcenter.model.BackLogAdapter;
import com.legend.ffpmvp.main.personalcenter.model.BackLogModelImpl;
import com.legend.ffpmvp.main.personalcenter.model.IBackLogModel;
import com.legend.ffpmvp.main.personalcenter.presenter.BackLogPresenter;
import com.legend.ffpmvp.main.personalcenter.presenter.IBackLogPresenter;

import static com.legend.ffpmvp.common.bean.Status.DELETE_OK;
import static com.legend.ffpmvp.common.bean.Status.NO_EXIT;
import static com.legend.ffpmvp.common.bean.Status.NO_PERMISSION;
import static com.legend.ffpmvp.common.bean.Status.UNKNOW_ERROR;
import static com.legend.ffpmvp.common.bean.Status.UPDATE_OK;
import static com.legend.ffpmvp.main.personalcenter.presenter.BackLogPresenter.adapter;

/**
 * @author Legend
 * @data by on 2018/3/1.
 * @description
 */

public class BackLogActivity extends BaseActivity implements IBackLogView {

    private XRecyclerView mRecyclerView;
    private IBackLogModel model;
    private IBackLogPresenter presenter;

    @Override
    public Object setResourceLayout() {
        return R.layout.backlog_layout;
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
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void operateResult(int code) {
        switch (code) {
            case DELETE_OK:
                ToastUtils.showToast(this,"您已成功删除该条计划");
                refreshData();
                break;
            case UPDATE_OK:
                ToastUtils.showToast(this,"您已成功完成该条计划，请尝试刷新");
                refreshData();
                break;
            case NO_EXIT:
                ToastUtils.showToast(MyApplication.getInstance(),"当前计划已经被删除 请尝试刷新");
                refreshData();
                break;
            case NO_PERMISSION:
                ToastUtils.showToast(MyApplication.getInstance(),"您没有该操作权限");
                break;
            case UNKNOW_ERROR:
                ToastUtils.showToast(MyApplication.getInstance(),"未知的错误");
                break;
            default:
                break;
        }
    }


    @Override
    public void initView() {
        model = new BackLogModelImpl();
        presenter = new BackLogPresenter(model,this);

        mRecyclerView = $(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
        adapter.setOnItemClickListener(new BackLogAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClickListener(View view, int position, int id) {
                presenter.updatePlan(position,id);
            }

            @Override
            public void onDeleteClickListener(View view, int position) {
                presenter.deletePlan(position);
            }
        });
    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }

    public void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.onRefresh();
                mRecyclerView.refreshComplete();
            }
        },500);
    }
}
