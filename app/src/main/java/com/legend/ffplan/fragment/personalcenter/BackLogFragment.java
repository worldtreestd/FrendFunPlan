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
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.adapter.BackLogAdapter;
import com.legend.ffplan.common.util.DateUtils;
import com.legend.ffplan.common.viewimplement.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description
 */

public class BackLogFragment extends Fragment implements ICommonView {
    private View mView;
    private BackLogAdapter adapter;
    private XRecyclerView mRecyclerView;
    private List<HomePlanBean> plan_List = new ArrayList<>();
    private HomePlanBean[] homePlanBeans = {new HomePlanBean(DateUtils.getDate(),"好想拥抱你，拥抱错过的勇气，那天晚上满天星星，平行时空下的约定" ,"多年后的我"),
                                    new HomePlanBean(DateUtils.getDate(),"coding之路少不了bug和崩溃，但是那样怎样，哪怕不能运行，也要将代码写得漂亮","Legend")};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.backlog_layout,container,false);
        }
        plan_List.add(homePlanBeans[0]);
        initData();
        initView();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        adapter = new BackLogAdapter(plan_List);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 恭喜你没有待办事项了哦");
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.LineScalePulseOut);
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
                initData();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.LineScalePulseOut);
                loadmoreData();
            }
        });
    }
    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10;i++) {
                    plan_List.add(homePlanBeans[0]);
                    plan_List.add(homePlanBeans[1]);
                }
                adapter.notifyDataSetChanged();
            }
        },100);
    }
    private void loadmoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                plan_List.clear();
                for (int i=0;i < 15;i++) {
                    plan_List.add(homePlanBeans[0]);
                    plan_List.add(homePlanBeans[1]);
                }
                adapter.notifyDataSetChanged();
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            }
        },2000);
    }
}
