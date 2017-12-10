package com.legend.ffplan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.legend.ffplan.R;
import com.legend.ffplan.common.viewimplement.ICommonView;

/**
 * @author Legend
 * @data by on 2017/12/10.
 * @description
 */

public class PlanContentActivity extends AppCompatActivity implements ICommonView {

    public static final String PLAN_START_TIME = "plan_start_time";
    public static final String PLAN_END_TIME = "plan_end_time";
    public static final String PLAN_CONTENT = "plan_content";
    public static final String PLAN_FROM = "plan_from";

    private View mView;
    private Toolbar toolbar;
    private TextView plan_start_time;
    private TextView plan_content;
    private TextView plan_from;
    private Button join_plan_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.plan_content,null);
        super.onCreate(savedInstanceState);
        setContentView(mView);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("计划详情！");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String start_time = intent.getStringExtra(PlanContentActivity.PLAN_START_TIME);
        String content = intent.getStringExtra(PlanContentActivity.PLAN_CONTENT);
        String from = intent.getStringExtra(PlanContentActivity.PLAN_FROM);
        join_plan_btn = findViewById(R.id.join_plan);
        plan_start_time = mView.findViewById(R.id.plan_start_time);
        plan_content = findViewById(R.id.plan_content);
        plan_from = findViewById(R.id.plan_from);
        plan_start_time.setText("计划开始时间：  "+start_time);
        plan_content.setText("详情："+content);
        plan_from.setText("From: "+from);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        join_plan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
                builder.setTitle("您是否要加入该计划");
                builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar.make(mView, "您已经成功加入该计划", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(PlanContentActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    }
                });
                builder.create().show();
            }
        });
    }
}
