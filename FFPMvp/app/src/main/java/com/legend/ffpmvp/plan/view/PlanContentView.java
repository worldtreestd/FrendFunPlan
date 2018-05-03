package com.legend.ffpmvp.plan.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.legend.ffpmvp.GuideAnimation.GuidePageActivity;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.plan.model.IPlanContentModel;
import com.legend.ffpmvp.plan.model.PlanContentModelImpl;
import com.legend.ffpmvp.plan.presenter.IPlanContentPresenter;
import com.legend.ffpmvp.plan.presenter.PlanContentPresenter;

import java.lang.ref.WeakReference;

import static com.legend.ffpmvp.common.bean.Status.ALREADY_JOIN;
import static com.legend.ffpmvp.common.bean.Status.NO_EXIT;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class PlanContentView extends BaseActivity implements IPlanContentView {

    public static final String PLAN_START_TIME = "plan_start_time";
    public static final String PLAN_END_TIME = "plan_end_time";
    public static final String PLAN_CONTENT = "plan_content";
    public static final String PLAN_ADDRESS = "plan_address";
    public static final String PLAN_CREATED = "plan_created";
    public static final String PLAN_PART_NUM = "plan_part_num";
    public static final String PLAN_FROM = "plan_from";
    public static final String PLAN_ID = "plan_id";


    private TextView plan_start_time_tv,plan_end_time_tv,plan_content_tv,plan_from_tv,
            plan_created_tv,plan_part_tv,plan_address_tv;

    private Button join_plan_btn;
    private int plan_id;
    private int part_num;
    private AppCompatImageView imageView;
    private IPlanContentModel model;
    private IPlanContentPresenter presenter;

    @Override
    public Object setResourceLayout() {
        return R.layout.plan_content;
    }

    @Override
    public void initView() {
        model = new PlanContentModelImpl();
        WeakReference<PlanContentView> reference = new WeakReference<>(this);
        presenter = new PlanContentPresenter(model,reference.get());
        Intent intent = getIntent();
        plan_id = intent.getIntExtra(PLAN_ID,1);
        String start_time = intent.getStringExtra(PLAN_START_TIME);
        String end_time = intent.getStringExtra(PLAN_END_TIME);
        String created = intent.getStringExtra(PLAN_CREATED);
        String content = intent.getStringExtra(PLAN_CONTENT);
        String from = intent.getStringExtra(PLAN_FROM);
        part_num = intent.getIntExtra(PLAN_PART_NUM,0);
        String address = intent.getStringExtra(PLAN_ADDRESS);
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
        imageView = $(R.id.imageview);
        runOnUiThread(() -> Glide.with(PlanContentView.this).load("https://api.dujin.org/bing/1920.php").into(imageView));
        // 0是进入这个页面时 1是请求参与计划时
        presenter.partPlan(plan_id,0);
    }

    @Override
    public void initListener() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("您是否要加入该计划");
        builder.setPositiveButton("是的", (dialogInterface, i) -> presenter.partPlan(plan_id,1));
        join_plan_btn.setOnClickListener((view)->{
            if (join_plan_btn.getText().toString().equals("已加入")) {
                ToastUtils.showToast(view.getContext(), "已加入该计划！");
            } else {
                builder.create().show();
            }
        });
    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }

    @Override
    public void updateUi() {
        part_num++;
        plan_part_tv.setText("现参与人数：\n      "+part_num);
        join_plan_btn.setText("已加入");
        Snackbar.make(imageView,"您已经成功加入该计划", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMsg(int code) {
        switch (code) {
            case ALREADY_JOIN:
                join_plan_btn.setText("已加入");
                break;
            case UN_LOGIN:
                ToastUtils.showToast(MyApplication.getInstance(),"请登录后再进行操作哦");
                GuidePageActivity.isLogin = false;
                break;
            case NO_EXIT:
                break;
            default:
                break;
        }

    }
}
