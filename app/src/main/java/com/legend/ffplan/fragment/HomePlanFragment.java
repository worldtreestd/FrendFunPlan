package com.legend.ffplan.fragment;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.adapter.PlanListAdapter;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.common.viewimplement.ICommonView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 全部计划列表页面
 */

public class HomePlanFragment extends Fragment implements ICommonView {

    private View mView;
    private XRecyclerView mRecyclerView;
    private PlanListAdapter adapter;
    private int mCurrentPageIndex = 1;
    private List<HomePlanBean> plan_list = new ArrayList<>();
    private HomePlanBean homePlanBean;
    private HomePlanAsyncTask asyncTask;
    private JSONArray result;
    private int count;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.home_layout,container,false);
        }
        initView();
        refreshData();
        initListener();
        return mView;
    }
    @Override
    public void initListener() {


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
    public void initView() {

        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setFootViewText("正在玩命加c载中...⌇●﹏●⌇","亲(o~.~o) 我也是有底线的哦");
        // 设置footview的最小高度 需要设置在导航栏上 否则会被遮盖
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        dialog = ToastUtils.createLoadingDialog(mView.getContext(),getString(R.string.common_loading));
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PlanListAdapter(plan_list);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     *  刷新数据
     */
    private void refreshData() {
        plan_list.clear();
        mCurrentPageIndex = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new HomePlanAsyncTask().execute(ApiUtils.PLANS+"?page="+mCurrentPageIndex);
                mRecyclerView.refreshComplete();
            }
        },500);
    }

    /**
     *  加载更多
     */
    private void loadMoreData() {
        mCurrentPageIndex++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getItemCount() >= count) {
                    mRecyclerView.setNoMore(true);
                } else {
                    new HomePlanAsyncTask().execute(ApiUtils.PLANS+"?page="+mCurrentPageIndex);
                    mRecyclerView.loadMoreComplete();
                }
            }
        },500);
    }
    /**
     *  异步加载Json数据
     */
    class HomePlanAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<HomePlanBean> doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            String data = response.getData().toString();
            try {
                JSONObject jsonObject = new JSONObject(data);
                count = jsonObject.getInt("count");
                result = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomePlanBean> planBeanList =
                    gson.fromJson(result.toString(),new TypeToken<List<HomePlanBean>>(){}.getType());
            return planBeanList;
        }

        @Override
        protected void onPostExecute(List<HomePlanBean> homePlanBeans) {
            super.onPostExecute(homePlanBeans);
            for (HomePlanBean plan : homePlanBeans) {
                homePlanBean =
                        new HomePlanBean(plan.getId(),plan.getAdd_time(),plan.getContent(),plan.getFrom_circle_name(),plan.getUser(),
                                plan.getAddress(),plan.getUsers_num(),plan.getEnd_time().replace("T","-").substring(0,19));
                plan_list.add(homePlanBean);
            }
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }
    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }
}
