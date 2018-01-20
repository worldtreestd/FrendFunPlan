package com.legend.ffplan.fragment.personalcenter;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.legend.ffplan.fragment.BaseFragment;

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

public class BackLogFragment extends BaseFragment {
    private View mView;
    public static final String STATUS = "status";
    private BackLogAdapter adapter;
    private XRecyclerView mRecyclerView;
    private HomePlanBean homePlanBean;
    private BackLogAsyncTask asyncTask;
    private JSONArray jsonArray;
    private List<HomePlanBean> plan_List = new ArrayList<>();
    private List<String> plan_list = new ArrayList<>();

    @Override
    public int setResourceLayoutId() {
        return R.layout.backlog_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }

    @Override
    public void initView() {
        mView = getmView();
        adapter = new BackLogAdapter(plan_List);
        refreshData();
    }

    @Override
    public void initListener() {
        mRecyclerView = getmRecyclerView();
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 恭喜你没有待办事项了哦");
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


    public void refreshData() {
        plan_list.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                  new BackLogAsyncTask().execute(ApiUtils.PARTPLANS+"?search=0");
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
                                plan.getAddress(),plan.getUsers_num(),plan.getEnd_time().replace("T","-").substring(0,19),plan.getFrom_circle());
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
