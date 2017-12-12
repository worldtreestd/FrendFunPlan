package com.legend.ffplan.activity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/9.
 * @description
 */

public class CircleSearchActivity extends AppCompatActivity implements ICommonView {

    private android.support.v7.widget.Toolbar toolbar;
    private SearchView searchView;
    private CircleSearchAsyncTask asyncTask;
    private AutoCompleteTextView mSearchAutoComplete;
    private XRecyclerView mRecyclerView;
    private CircleListAdapter adapter;
    private List<HomeCircleBean> circleList = new ArrayList<>();
    private HomeCircleBean circleBean;
    private JSONArray result;
    private List<HomeCircleBean> circleBeanList;
    private int count;
    private Dialog dialog;
    private View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.search_layout,null);
        setContentView(mView);
        initView();
        initListener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.onActionViewExpanded();
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);//默认为true在框内，设置false则在框外
        mSearchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        //设置输入框提示文字样式
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));//设置内容文字颜色
        searchView.setQueryHint("请您输入圈子的ID号或简介");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setFootViewText("正在玩命加载中...⌇●﹏●⌇","亲(o~.~o) 我也是有底线的哦");
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        dialog = ToastUtils.createLoadingDialog(this,getString(R.string.common_loading));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new XRecyclerViewDivider(this,LinearLayoutManager.HORIZONTAL));
        adapter = new CircleListAdapter(circleList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchAutoComplete.isShown()) {
                    try {
                        mSearchAutoComplete.setText("");//清除文本
                        //利用反射调用收起SearchView的onCloseClicked()方法
                        Method method = searchView.getClass().getDeclaredMethod("onCloseClicked");
                        method.setAccessible(true);
                        method.invoke(searchView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    finish();
                }
            }
        });
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshData("");
            }

            @Override
            public void onLoadMore() {
//                loadmoreData();
            }
        });
    }
    private void refreshData(final String query) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                asyncTask = (CircleSearchAsyncTask) new CircleSearchAsyncTask()
                            .execute(ApiUtils.SEARCH + query);
                mRecyclerView.refreshComplete();
            }
        },500);

    }
    class CircleSearchAsyncTask extends AsyncTask<String,Void,List<HomeCircleBean>> {

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
            List<HomeCircleBean> circleList =
                    gson.fromJson(result.toString(),new TypeToken<List<HomeCircleBean>>(){}.getType());
            return circleList;
        }

        @Override
        protected void onPostExecute(List<HomeCircleBean> list) {
            super.onPostExecute(list);
            circleList.clear();
            for (HomeCircleBean circle : list) {
                circleBean =
                        new HomeCircleBean(circle.getId(),circle.getImage(),circle.getName(),circle.getDesc(),
                                circle.getUser(),circle.getAddress(),circle.getAdd_time());
                circleList.add(circleBean);
            }
            if (circleList != null) {
                Toast.makeText(CircleSearchActivity.this, "查找成功！", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }
}
