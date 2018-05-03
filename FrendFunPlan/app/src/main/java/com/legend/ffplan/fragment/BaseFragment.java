package com.legend.ffplan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * @author Legend
 * @data by on 2018/1/13.
 * @description
 */

abstract public class BaseFragment extends Fragment {

    private View mView;
    private Context mContext;
    private XRecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setResourceLayoutId(),container,false);
        mContext = getContext();
        initView();
        mRecyclerView = $(setRecyclerViewId());
        if (mRecyclerView != null) {
            init();
        }
        refreshData();
        initListener();
        return mView;
    }
    private void init() {
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 我也是有底线的哦");
        // 设置footview的最小高度 需要设置在导航栏上 否则会被遮盖
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public abstract int setResourceLayoutId();

    public abstract int setRecyclerViewId();

    public abstract void initView();

    public abstract void initListener();

    public abstract void refreshData();

    public <T extends View> T $(int id) {
        return (T)mView.findViewById(id);
    }

    public View getmView() {
        return mView;
    }

    public XRecyclerView getmRecyclerView() {
        return mRecyclerView;
    }
    public Context getmContext() {
        return mContext;
    }
}
