package com.legend.ffplan.fragment;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
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
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.common.view.XRecyclerViewDivider;
import com.legend.ffplan.common.viewimplement.ICommonView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 添加计划页面
 */

public class HomeCircleFragment extends Fragment implements ICommonView{

    public static final String SEARCH_CIRCLE_RESULTS = "search_circle_result";

    private View mView;
    private XRecyclerView mRecyclerView;
    private CircleListAdapter adapter;
    private List<HomeCircleBean> circleList = new ArrayList<>();
    private HomeCircleBean circleBean;
    private int mCurrentPageIndex = 1;
    private HomeCircleAsyncTask asyncTask;
    private JSONArray result;
    private List<HomeCircleBean> circleBeanList;
    private int count;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView ==  null) {
            mView = inflater.inflate(R.layout.all_circle_layout,container,false);
        }
        initView();
        refreshData();
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
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        dialog = ToastUtils.createLoadingDialog(mView.getContext(),getString(R.string.common_loading));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new XRecyclerViewDivider(mView.getContext(),LinearLayoutManager.HORIZONTAL));
        adapter = new CircleListAdapter(circleList);
        mRecyclerView.setAdapter(adapter);
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
                loadmoreData();
            }
        });
    }

    /**
     *  刷新数据
     */
    private void refreshData() {
        circleList.clear();
        mCurrentPageIndex = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new HomeCircleAsyncTask().execute(ApiUtils.CIRCLES+"?page="+mCurrentPageIndex);
                mRecyclerView.refreshComplete();
            }
        },500);

    }
    private void loadmoreData() {
        mCurrentPageIndex++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getItemCount() >= count) {
                    mRecyclerView.setNoMore(true);
                } else {
                    new HomeCircleAsyncTask().execute(ApiUtils.CIRCLES+"?page="+mCurrentPageIndex);
                    mRecyclerView.loadMoreComplete();
                }
            }
        },500);
    }

    /**
     *  返回搜索结果
     * @throws JSONException
     */
    private void refreshSearch() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String data = bundle.getString(SEARCH_CIRCLE_RESULTS);
            if (!TextUtils.isEmpty(data)) {
            }
        }
    }
        /**
         *  异步加载Json数据
         */
    class HomeCircleAsyncTask extends AsyncTask<String, Void, List<HomeCircleBean>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<HomeCircleBean> doInBackground(String... strings) {
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
            circleBeanList =
                    gson.fromJson(result.toString(), new TypeToken<List<HomeCircleBean>>() {
                    }.getType());

            return circleBeanList;
        }

        @Override
        protected void onPostExecute(List<HomeCircleBean> homeCircleBeans) {
            super.onPostExecute(homeCircleBeans);

            for (HomeCircleBean circle : homeCircleBeans) {
                circleBean =
                        new HomeCircleBean(circle.getId(), circle.getImage(), circle.getName(), circle.getDesc(),
                                circle.getUser(), circle.getAddress(), circle.getAdd_time());
                circleList.add(circleBean);
            }
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }
}
