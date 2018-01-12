package com.legend.ffplan.fragment.personalcenter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.adapter.BackLogAdapter;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.viewimplement.ICommonView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description 待办事项Fragment
 */

public class BackLogFragment extends Fragment implements ICommonView {
    private View mView;
    public static final String STATUS = "status";
    private BackLogAdapter adapter;
    private XRecyclerView mRecyclerView;
    private List<HomePlanBean> plan_List = new ArrayList<>();
    private HomePlanBean homePlanBean;
    private BackLogAsyncTask asyncTask;
    private JSONArray jsonArray;
    private List<String> plan_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.backlog_layout,container,false);
        }
        initView();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.getFootView().setMinimumHeight(400);
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 恭喜你没有待办事项了哦");
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        LinearLayoutManager manager = new LinearLayoutManager(mView.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new BackLogAdapter(plan_List);
        mRecyclerView.setAdapter(adapter);
        refreshData();
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
//                loadmoreData();
            }
        });
    }


    public void refreshData() {
        plan_list.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                  new BackLogAsyncTask().execute(ApiUtils.PARTPLANS);
                mRecyclerView.refreshComplete();
            }
        },500);

    }

    class BackLogAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

        @Override
        protected List<HomePlanBean> doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared =
                    new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            IHttpClient mHttpclient = new OkHttpClientImpl();
            IResponse response = mHttpclient.get(request);
            String data = response.getData().toString();
            try {
                jsonArray = new JSONArray(data);
                for (int i=0;i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String plan = jsonObject.getString("plan" );
                    plan_list.add(plan);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomePlanBean> homePlanBeans =
                    gson.fromJson(plan_list.toString(),new TypeToken<List<HomePlanBean>>(){}.getType());
            return homePlanBeans;
        }

        @Override
        protected void onPostExecute(List<HomePlanBean> homePlanBeans) {
            super.onPostExecute(homePlanBeans);
            plan_List.clear();
            for (HomePlanBean plan : homePlanBeans) {
                homePlanBean =
                        new HomePlanBean(plan.getId(),plan.getAdd_time(),plan.getContent(),plan.getFrom_circle_name(),plan.getUser(),
                                plan.getAddress(),plan.getUsers_num(),plan.getEnd_time().replace("T","-").substring(0,19));
                plan_List.add(homePlanBean);
            }
            adapter.notifyDataSetChanged();
        }
    }
//    private void loadmoreData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                plan_List.clear();
//                for (int i=0;i < 15;i++) {
//                    plan_List.add(homePlanBeans[0]);
//                    plan_List.add(homePlanBeans[1]);
//                }
//                adapter.notifyDataSetChanged();
//                mRecyclerView.loadMoreComplete();
//                mRecyclerView.setNoMore(true);
//            }
//        },2000);
//    }
}
