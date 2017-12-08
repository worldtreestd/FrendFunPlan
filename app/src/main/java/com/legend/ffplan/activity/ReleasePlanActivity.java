package com.legend.ffplan.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.legend.ffplan.MainActivity;
import com.legend.ffplan.R;
import com.legend.ffplan.common.viewimplement.ICommonView;

import me.james.biuedittext.BiuEditText;

/**
 * @author Legend
 * @data by on 2017/12/6.
 * @description 发布计划Activity
 */

public class ReleasePlanActivity extends AppCompatActivity implements ICommonView{

    private Toolbar toolbar;
    private BiuEditText set_date_end;
    private CardView release_plan;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_plan_layout);
        initView();
        initListener();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("发布一条计划！");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        set_date_end = findViewById(R.id.date_end);

        release_plan = findViewById(R.id.release_plan);
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
                        set_date_end.setFocusable(false);
                        set_date_end.setText(String.format("%d--%d--%d",i,i1,i2).toString());
                    }
                },2017,11,1).show();
            }
        });
        release_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReleasePlanActivity.this,"恭喜您成功发布一条计划！",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ReleasePlanActivity.this, MainActivity.class).putExtra("id","0"));
            }
        });

    }
}
