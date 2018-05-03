package com.legend.ffplan.fragment.personalcenter;


import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomeCircleBean;
import com.legend.ffplan.common.adapter.CircleListAdapter;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.view.XRecyclerViewDivider;
import com.legend.ffplan.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description 我的圈子Fragment
 */

public class MyCircleFragment extends BaseFragment{

    private View mView;
    private XRecyclerView mRecyclerView;
    private CircleListAdapter adapter;
    private List<HomeCircleBean> circle_List = new ArrayList<>();
    private HomeCircleBean circleBean;
    private MyCircleAsyncTask asyncTask;
    private JSONArray jsonArray1;
    private List<String> circle_list = new ArrayList<>();

    @Override
    public int setResourceLayoutId() {
        return R.layout.mycircle_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }

    @Override
    public void initView() {
        mView = getmView();
        adapter = new CircleListAdapter(circle_List);
    }

    @Override
    public void initListener() {
        mRecyclerView = getmRecyclerView();
        mRecyclerView.addItemDecoration(new XRecyclerViewDivider(mView.getContext(), LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
//                loadmoreData();
            }
        });
    }

    @Override
    public void refreshData() {
        circle_list.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MyCircleAsyncTask().execute(ApiUtils.PARTCIRCLE);
                mRecyclerView.refreshComplete();
            }
        },1000);
    }
    class MyCircleAsyncTask extends AsyncTask<String,Void,List<HomeCircleBean>> {

        @Override
        protected List<HomeCircleBean> doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared =
                    new SharedPreferenceUtils(MyApplication.getInstance(), SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT " + shared.get(SharedPreferenceUtils.ACCOUNTJWT));

            IHttpClient mHttpclient = new OkHttpClientImpl();
            IResponse response = mHttpclient.get(request);
            String data = response.getData().toString();
            try {
                jsonArray1 = new JSONArray(data);
                for (int i=0;i < jsonArray1.length();i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    String circle = jsonObject.getString("circle");
                    circle_list.add(circle);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomeCircleBean> homeCircleBeans =
                    gson.fromJson(circle_list.toString(), new TypeToken<List<HomeCircleBean>>() {
                    }.getType());
            return homeCircleBeans;
        }

        @Override
        protected void onPostExecute(List<HomeCircleBean> homeCircleBeans) {
            super.onPostExecute(homeCircleBeans);
            circle_List.clear();
            for (HomeCircleBean circle : homeCircleBeans) {
                circleBean =
                        new HomeCircleBean(circle.getId(), circle.getImage(), circle.getName(), circle.getDesc(),
                                circle.getUser(), circle.getAddress(), circle.getAdd_time());
                circle_List.add(circleBean);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
