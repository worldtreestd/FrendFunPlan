package com.legend.ffplan.fragment.personalcenter;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.MyCircleBean;
import com.legend.ffplan.common.adapter.MyCircleAdapter;
import com.legend.ffplan.common.viewimplement.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description
 */

public class MyCircleFragment extends Fragment implements ICommonView{

    private View mView;
    private XRecyclerView mRecyclerView;
    private MyCircleAdapter adapter;
    private List<MyCircleBean> circleList = new ArrayList<>();
    private MyCircleBean[] CircleBeans = {new MyCircleBean("世界树项目组"),
                                         new MyCircleBean("Coding之路")};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.mycircle_layout,container,false);
        }
        initData();
        initView();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 我也是有底线的哦");
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new MyCircleAdapter(circleList);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
                loadmoreData();
            }
        });
    }
    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circleList.clear();
                for (int i =0;i < 10;i++) {
                    circleList.add(CircleBeans[0]);
                    circleList.add(CircleBeans[1]);
                }
                adapter.notifyDataSetChanged();
            }
        },2000);
    }
    private void loadmoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circleList.clear();
                for (int i =0;i < 15;i++) {
                    circleList.add(CircleBeans[0]);
                    circleList.add(CircleBeans[1]);
                }
                adapter.notifyDataSetChanged();
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            }
        },2000);

    }
}
