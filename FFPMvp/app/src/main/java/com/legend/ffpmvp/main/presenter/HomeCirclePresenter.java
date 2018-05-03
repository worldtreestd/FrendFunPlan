package com.legend.ffpmvp.main.presenter;

import android.util.Log;

import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.adapter.CircleListAdapter;
import com.legend.ffpmvp.common.view.banner.BannerViewAdapter;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.view.ICommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class HomeCirclePresenter implements ICommonPresenter {

    public static CircleListAdapter adapter;
    public static BannerViewAdapter bannerAdapter;
    private ICommonModel circleModel;
    private ICommonView circleView;
    private int mCurrentPageIndex = 1;
    private List<HomeCircleBean> circleBeanLists;
    private List<HomeCircleBean> circleBanners;


    public HomeCirclePresenter(ICommonModel circleModel, ICommonView circleView) {
        this.circleModel = circleModel;
        this.circleView = circleView;
        this.circleBeanLists = new ArrayList<>();
        this.circleBanners = new ArrayList<>();
        adapter = new CircleListAdapter(circleBeanLists,1);
        bannerAdapter = new BannerViewAdapter(circleBanners);
    }
    @Override
    public void onRefresh() {
        mCurrentPageIndex = 1;
        circleModel.getCircleBanner(new ICommonModel.RequestResult() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List beanList) {
                circleBanners.clear();
                circleBanners.addAll(beanList);
                Log.d("nbansasasasa",circleBanners.toString());
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                bannerAdapter.notifyDataSetChanged();
            }
        });
        circleModel.getData(mCurrentPageIndex, new ICommonModel.RequestResult() {

            @Override
            public void onStart() {
                circleView.showLoading();
            }

            @Override
            public void onSuccess(List beanList) {
                circleBeanLists.clear();
                circleBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                adapter.notifyDataSetChanged();
                circleView.hideLoading();
            }
        });

    }

    @Override
    public void onLoadMore() {
        mCurrentPageIndex++;
        circleModel.getData(mCurrentPageIndex, new ICommonModel.RequestResult() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List beanList) {
                circleBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() >= circleModel.count()) {
                    circleView.noMoreData();
                } else {
                    circleView.loadMoreComplete();
                }
            }
        });
    }
}
