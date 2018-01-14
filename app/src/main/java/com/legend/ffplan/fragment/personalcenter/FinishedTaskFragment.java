package com.legend.ffplan.fragment.personalcenter;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.adapter.PlanListAdapter;
import com.legend.ffplan.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description 已完任务Fragment
 */

public class FinishedTaskFragment extends BaseFragment {

    private View mView;
    private XRecyclerView mRecyclerView;
    private PlanListAdapter adapter;
    private List<HomePlanBean> plan_list = new ArrayList<>();

    @Override
    public int setResourceLayoutId() {
        return R.layout.finishedtask_layout;
    }

    @Override
    public void initView() {
        mView = getmView();
        mRecyclerView = $(R.id.mRecyclerView);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setFootViewText("正在玩命加c载中...⌇●﹏●⌇","亲(o~.~o) 我也是有底线的哦");
        adapter = new PlanListAdapter(plan_list);
        LinearLayoutManager manager = new LinearLayoutManager(mView.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
                initData();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);
                loadMoreData();
            }
        });
    }

    @Override
    public void refreshData() {

    }

    private void initData() {
        plan_list.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10;i++) {
//                    plan_list.add(homePlanBeans[0]);
//                    plan_list.add(homePlanBeans[1]);
                }
                adapter.notifyDataSetChanged();
            }
        },2000);
    }
    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                plan_list.clear();
                int i;
                for (i = 0;i < 33;i++) {
//                    plan_list.add(homePlanBeans[0]);
//                    plan_list.add(homePlanBeans[1]);
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"加载了"+i*2+"条数据",Toast.LENGTH_SHORT).show();
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            }
        },2000);
    }
}
