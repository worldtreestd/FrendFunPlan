package com.legend.ffplan.fragment.circlecenter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.activity.CircleContentActivity;
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.adapter.PlanListAdapter;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子内的计划
 */

public class CirclePlanFragment extends BaseFragment {

    private View mView;
    private XRecyclerView mRecyclerView;
    private PlanListAdapter adapter;
    private String plan_data;
    private List<HomePlanBean> plan_list = new ArrayList<>();
    private HomePlanBean homePlanBean = new HomePlanBean("","","");
    private CirclePlanAsyncTask asyncTask;

    @Override
    public int setResourceLayoutId() {
        return R.layout.circle_plan_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }


    @Override
    public void initView() {
        mView = getmView();
    }

    @Override
    public void initListener() {
        mRecyclerView.getFootView().setMinimumHeight(200);
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
    public void refreshData() {
        mRecyclerView = getmRecyclerView();
        Intent intent = getActivity().getIntent();
        int circle_id = intent.getIntExtra(CircleContentActivity.CIRCLE_ID, 0);
        if (ToastUtils.checkNetState(mView.getContext())) {
            if (asyncTask == null) {
                asyncTask = (CirclePlanAsyncTask) new CirclePlanAsyncTask().execute(ApiUtils.CIRCLES + circle_id);
            }
            mRecyclerView.refreshComplete();
        } else {
            ToastUtils.showToast(mView.getContext(), "您的网络连接有误 请检查一下连接状态");
        }
    }
    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                plan_list.clear();
                int i;
//                for (i = 0;i < 33;i++) {
//                    plan_list.add(homePlanBeans[0]);
//                    plan_list.add(homePlanBeans[1]);
//                }
                adapter.notifyDataSetChanged();
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            }
        },1000);
    }
    class CirclePlanAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

        @Override
        protected List<HomePlanBean> doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            String circle_data = response.getData();
            try {
                JSONObject jsonObject = new JSONObject(circle_data);
                plan_data =
                        jsonObject.getString("plan_list");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomePlanBean> homePlanBeans =
                    gson.fromJson(plan_data,new TypeToken<List<HomePlanBean>>(){}.getType());
            return homePlanBeans;
        }

        @Override
        protected void onPostExecute(List<HomePlanBean> homePlanBeans) {
            super.onPostExecute(homePlanBeans);

            for (HomePlanBean plan : homePlanBeans) {
                homePlanBean =
                        new HomePlanBean(plan.getId(),plan.getAdd_time(),plan.getContent(),plan.getFrom_circle_name(),plan.getUser(),
                                plan.getAddress(),plan.getUsers_num(),plan.getEnd_time(),plan.getFrom_circle());
                plan_list.add(homePlanBean);
            }
            adapter = new PlanListAdapter(plan_list);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}