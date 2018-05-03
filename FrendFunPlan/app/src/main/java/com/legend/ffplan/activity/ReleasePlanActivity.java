package com.legend.ffplan.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import com.legend.ffplan.MainActivity;
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

import java.util.HashMap;
import java.util.Map;

import me.james.biuedittext.BiuEditText;

/**
 * @author Legend
 * @data by on 2017/12/6.
 * @description 发布计划Activity
 */

public class ReleasePlanActivity extends BaseActivity{

    private View mView;
    private Toolbar toolbar;
    private BiuEditText set_date_end,plan_content,circle_id;
    private CardView release_plan;
    private DatePickerDialog datePickerDialog;
    private ReleasePlanAsyncTask asyncTask;
    private int id;
    private Dialog dialog;
    private String content;
    private String time;

    @Override
    public int setResourceLayout() {
        return R.layout.release_plan_layout;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void initView() {
        toolbar = $(R.id.toolbar);
        toolbar.setTitle("发布一条计划！");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        set_date_end = $(R.id.date_end);
        plan_content = findViewById(R.id.plan_content);
        release_plan = $(R.id.release_plan);
        circle_id = $(R.id.circle_id);
        dialog = ToastUtils.createLoadingDialog(this, getString(R.string.release_plan_loading));
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        set_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        /**
                         *  这里设置失去聚焦 因为biuEditText是通过反射来获取
                         *  光标位置 在initView里面设置会导致直接失去光标
                         *  进而引起空指针异常
                         */
                        set_date_end.setFocusable(false);
                        set_date_end.setText(String.format("%d-%d-%d 23:59:59",i,i1,i2).toString());
                    }
                },2017,11,1).show();
            }
        });
        release_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String circleid = circle_id.getText().toString();
                if (!TextUtils.isEmpty(circleid)) {
                    id = Integer.parseInt(circleid);
                }
                time = set_date_end.getText().toString();
                content = plan_content.getText().toString();
                if (TextUtils.isEmpty(time) ||TextUtils.isEmpty(content) ) {
                    ToastUtils.showToast(ReleasePlanActivity.this,"还有字段为空哦");
                } else {
                    new ReleasePlanAsyncTask().execute(ApiUtils.PLANS);
                }
            }
        });
    }
    class ReleasePlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            Map<String,Object> map = new HashMap<>();
            map.put("end_time",time);
            map.put("content",content);
            map.put("from_circle",id);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.upload_image_post(request,map,null);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == 400) {
                ToastUtils.showToast(ReleasePlanActivity.this,"当前输入的圈子不存在哦");
                dialog.dismiss();
                return;
            }
            if (iResponse.getCode() == 201) {
                dialog.dismiss();
                ToastUtils.showToast(ReleasePlanActivity.this,"恭喜您成功发布一条计划！");
                startActivity(new Intent(ReleasePlanActivity.this, MainActivity.class).putExtra("id","1"));
            } else {
                ToastUtils.showToast(ReleasePlanActivity.this,"网络出错");
                dialog.dismiss();
            }
        }
    }
}
