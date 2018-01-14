package com.legend.ffplan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.legend.ffplan.R;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.fragment.personalcenter.BackLogFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Legend
 * @data by on 2017/12/10.
 * @description 计划详情界面
 */

public class PlanContentActivity extends BaseActivity {

    public static final String PLAN_START_TIME = "plan_start_time";
    public static final String PLAN_END_TIME = "plan_end_time";
    public static final String PLAN_CONTENT = "plan_content";
    public static final String PLAN_ADDRESS = "plan_address";
    public static final String PLAN_CREATED = "plan_created";
    public static final String PLAN_PART_NUM = "plan_part_num";
    public static final String PLAN_FROM = "plan_from";
    public static final String PLAN_ID = "plan_id";

    private View mView;
    private Toolbar toolbar;
    private TextView plan_start_time_tv,plan_end_time_tv,plan_content_tv,plan_from_tv,
                    plan_created_tv,plan_part_tv,plan_address_tv;
    public boolean isPart = false;
    private String status;
    private Button join_plan_btn;
    private int plan_id;
    private JSONObject jsonObject;
    private String error_message;
    private int part_num;

    @Override
    public int setResourceLayout() {
        return R.layout.plan_content;
    }

    @Override
    public void initView() {
        toolbar = $(R.id.toolbar);
        toolbar.setTitle("计划详情！");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        plan_id = intent.getIntExtra(PlanContentActivity.PLAN_ID,1);
        String start_time = intent.getStringExtra(PlanContentActivity.PLAN_START_TIME);
        String end_time = intent.getStringExtra(PlanContentActivity.PLAN_END_TIME);
        String created = intent.getStringExtra(PlanContentActivity.PLAN_CREATED);
        String content = intent.getStringExtra(PlanContentActivity.PLAN_CONTENT);
        String from = intent.getStringExtra(PlanContentActivity.PLAN_FROM);
        part_num = intent.getIntExtra(PlanContentActivity.PLAN_PART_NUM,0);
        String address = intent.getStringExtra(PlanContentActivity.PLAN_ADDRESS);
        status = intent.getStringExtra(BackLogFragment.STATUS);
        join_plan_btn = $(R.id.join_plan);

        plan_start_time_tv = $(R.id.plan_start_time);
        plan_end_time_tv = $(R.id.plan_end_time);
        plan_created_tv = $(R.id.plan_created);
        plan_content_tv = $(R.id.plan_content);
        plan_from_tv = $(R.id.plan_from);
        plan_address_tv = $(R.id.plan_address);
        plan_part_tv = $(R.id.plan_part_num);

        plan_start_time_tv.setText("  计划开始时间： \n"+start_time);
        plan_content_tv.setText("详情："+content);
        plan_from_tv.setText(" From: "+from);
        plan_end_time_tv.setText("结束时间："+end_time);
        plan_created_tv.setText("发布者："+created);
        plan_address_tv.setText("地点： "+address);
        plan_part_tv.setText("现参与人数：\n      "+part_num);
    }

    @Override
    public void initListener() {
        if (!TextUtils.isEmpty(status)) {
            join_plan_btn.setText(status);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        join_plan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanContentActivity.this);
                builder.setTitle("您是否要加入该计划");
                builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new PartPlanAsyncTask().execute(ApiUtils.PARTPLANS);
                        Snackbar.make(view, "您已经成功加入该计划", Snackbar.LENGTH_LONG).show();
                    }
                });
                builder.create().show();
            }
        });
        new PartPlanAsyncTask().execute(ApiUtils.PARTPLANS+plan_id);
    }
    class PartPlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("plan",plan_id);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.post(request);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            String data = iResponse.getData().toString();
            try {
                jsonObject = new JSONObject(data);
                error_message = jsonObject.getString("non_field_errors");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (iResponse.getCode() == 400 || iResponse.getCode() == 200) {
                if (!TextUtils.isEmpty(error_message)) {
                        ToastUtils.showToast(MyApplication.getInstance(),error_message+"");
                    }
                join_plan_btn.setText("已加入");
            } else if (iResponse.getCode() == 201){
                part_num++;
                plan_part_tv.setText("现参与人数：\n      "+part_num);
                ToastUtils.showToast(MyApplication.getInstance(),"加入成功");
                join_plan_btn.setText("已加入");
            }
        }
    }
}
