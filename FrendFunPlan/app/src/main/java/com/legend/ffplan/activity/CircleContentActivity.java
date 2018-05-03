package com.legend.ffplan.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.legend.ffplan.R;
import com.legend.ffplan.common.adapter.CircleFragmentAdapter;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.common.view.HeartAnimationView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子详情界面
 */

public class CircleContentActivity extends BaseActivity {

    public static final String CIRCLE_NAME ="circle_name";
    public static final String CIRCLE_IMAGE_URL= "circle_image_URL";
    public static final String CIRCLE_INTRODUCE = "circle_introduce";
    public static final String CIRCLE_CREATED = "circle_created";
    public static final String CIRCLE_ADDRESS = "circle_address";
    public static final String CIRCLE_ID = "circle_id";
    public static final String CIRCLE_ADD_TIME = "circle_add_time";


    private View mView;
    private ViewPager mViewPager;
    private CircleFragmentAdapter adapter;
    private TabLayout tabLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private CollapsingToolbarLayout collaspingtoolbar;
    private FloatingActionButton join_circle_btn;
    private ImageView circle_image;
    private int circle_id;
    private HeartAnimationView heartAnimationView;

    @Override
    public int setResourceLayout() {
        return R.layout.circle_contenet;
    }
    @Override
    public void initView() {
        Intent intent = getIntent();
        String circle_image_url = intent.getStringExtra(CIRCLE_IMAGE_URL);
        String circle_name = intent.getStringExtra(CIRCLE_NAME);
        circle_id = intent.getIntExtra(CircleContentActivity.CIRCLE_ID,100000);
        toolbar = $(R.id.toolbar);
        join_circle_btn = $(R.id.join_circle);
        heartAnimationView = $(R.id.heart_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collaspingtoolbar = $(R.id.collapsing);
        collaspingtoolbar.setTitle(circle_name);
        // 设置收缩时title的位置
        collaspingtoolbar.setCollapsedTitleGravity(Gravity.TOP);
        // 设置展开时title的位置
        collaspingtoolbar.setExpandedTitleGravity(Gravity.CENTER_VERTICAL);
        adapter = new CircleFragmentAdapter(getSupportFragmentManager());

        circle_image = $(R.id.circle_content_image);


        Glide.with(this).load(circle_image_url).into(circle_image);

        tabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.mViewPager);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager,true);

    }
    @Override
    public void initListener() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                heartAnimationView.addHeart();
                handler.postDelayed(this,800);
            }
        };
        handler.postDelayed(runnable,800);
        join_circle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PartCircleAsyncTask().execute(ApiUtils.PARTCIRCLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.circle_menu,menu);
        return true;
    }
    class PartCircleAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("circle",circle_id);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.post(request);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == 400) {
                String data = iResponse.getData().toString();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    ToastUtils.showToast(MyApplication.getInstance(),jsonObject.getString("non_field_errors"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.showToast(MyApplication.getInstance(),"加入成功");
            }
        }
    }
}
