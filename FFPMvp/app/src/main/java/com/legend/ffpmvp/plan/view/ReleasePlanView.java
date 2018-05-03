package com.legend.ffpmvp.plan.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import com.legend.ffpmvp.GuideAnimation.GuidePageActivity;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.contract.CreateContract;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.main.view.MainActivity;
import com.legend.ffpmvp.plan.model.ReleasePlanModelImpl;
import com.legend.ffpmvp.plan.presenter.ReleasePlanPresenter;

import java.lang.ref.WeakReference;

import me.james.biuedittext.BiuEditText;

import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;
import static com.legend.ffpmvp.common.bean.Status.UNKNOW_ERROR;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;

/**
 * @author Legend
 * @data by on 2018/2/4.
 * @description
 */

public class ReleasePlanView extends BaseActivity implements CreateContract.ICreateView {

    private BiuEditText set_date_end,plan_content,circle_id;
    private CardView release_plan;
    private int id;
    private Dialog dialog;
    private String content;
    private String time;
    private CreateContract.ICreateModel model;
    private CreateContract.ICreatePresenter presenter;

    @Override
    public Object setResourceLayout() {
        return R.layout.release_plan_layout;
    }

    @Override
    public void initView() {
        model = new ReleasePlanModelImpl();
        WeakReference<ReleasePlanView> reference = new WeakReference<>(this);
        presenter = new ReleasePlanPresenter(model,reference.get());
        set_date_end = $(R.id.date_end);
        plan_content = findViewById(R.id.plan_content);
        release_plan = $(R.id.release_plan);
        circle_id = $(R.id.circle_id);
    }

    @Override
    public void initListener() {
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
                        set_date_end.setText(String.format("%d-%d-%d 23:33:33",i,i1+1,i2).toString());
                    }
                },2018,3,1).show();
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
                    ToastUtils.showToast(ReleasePlanView.this,"还有字段为空哦");
                } else {
                    if (id >= UNKNOW_ERROR) {
                        presenter.createExecute(null,time,content, String.valueOf(id));
                    } else {
                        circle_id.setError("请输入正确的6位圈子号");
                    }
                }
            }
        });
    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }

    @Override
    public void showLoading() {
        dialog = ToastUtils.createLoadingDialog(this, getString(R.string.release_plan_loading));
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showError(int code) {
        switch (code) {
            case JOIN_OK:
                ToastUtils.showToast(this,"恭喜您成功发布一条计划！");
                startActivity(new Intent(ReleasePlanView.this, MainActivity.class).putExtra("id","1"));

                break;
            case UN_LOGIN:
                ToastUtils.showToast(this,"请登录后再进行操作");
                GuidePageActivity.isLogin = false;
                break;
            case UNKNOW_ERROR:
                ToastUtils.showToast(this,"当前圈子号输入有误");
                break;
            default:
                break;
        }
    }
}
