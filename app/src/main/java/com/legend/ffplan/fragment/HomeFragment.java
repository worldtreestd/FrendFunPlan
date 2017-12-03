package com.legend.ffplan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.adapter.HomePlanAdapter;
import com.legend.ffplan.common.viewimplement.ICommonView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 主页面
 */

public class HomeFragment extends Fragment implements ICommonView {

    private View mView;
    private XRecyclerView mRecyclerView;
    private HomePlanAdapter adapter;
    private List<HomePlanBean> plan_list = new ArrayList<>();
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String start_time = dateFormat.format(date);
    private HomePlanBean[] homePlanBeans = {new HomePlanBean(start_time,"村声势浩大和你说的还能对你很好的汉莎安达的和你上次你说的不迪士尼的环境按时间","湖南信息职业技术学院"),
            new HomePlanBean(start_time,"村声势浩大和你说的还能对你很好的汉莎安达的和你上次你说的不迪士尼的环境按时间","南县一中")};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.home_layout,container,false);
        }
        initData();
        initView();
        initListener();
        return mView;
    }
    @Override
    public void initListener() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                initData();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
                loadMoreData();

            }
        });
    }
    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                plan_list.clear();
                int i;
                for (i = 0;i < 33;i++) {
                    plan_list.add(homePlanBeans[0]);
                    plan_list.add(homePlanBeans[1]);
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"加载了"+i*2+"条数据",Toast.LENGTH_SHORT).show();
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            }
        },2000);
    }
    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 我也是有底线的哦");
        // 设置footview的最小高度 需要设置在导航栏上 否则会被遮盖
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallGridBeat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HomePlanAdapter(plan_list);
        mRecyclerView.setAdapter(adapter);

    }
    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                plan_list.clear();
                for (int i = 0;i < 10;i++) {
                    plan_list.add(homePlanBeans[0]);
                    plan_list.add(homePlanBeans[1]);
                }
                adapter.notifyDataSetChanged();
            }
        },2000);
    }
}
