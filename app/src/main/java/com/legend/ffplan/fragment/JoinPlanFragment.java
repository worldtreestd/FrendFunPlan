package com.legend.ffplan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legend.ffplan.R;
import com.legend.ffplan.activity.CreateCircleActivity;
import com.legend.ffplan.activity.ReleasePlanActivity;
import com.legend.ffplan.common.viewimplement.ICommonView;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 添加计划页面
 */

public class JoinPlanFragment extends Fragment implements ICommonView{

    private View mView;
    private CardView create_circle;
    private CardView release_plan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView ==  null) {
            mView = inflater.inflate(R.layout.joinplan_layout,container,false);
        }
        initView();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        create_circle = mView.findViewById(R.id.create_circle);
        release_plan = mView.findViewById(R.id.send_plan);
    }

    @Override
    public void initListener() {
        create_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(JoinPlanFragment.this.getActivity(), CreateCircleActivity.class);
                startActivity(intent);
            }
        });
        release_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(JoinPlanFragment.this.getActivity(), ReleasePlanActivity.class);
                startActivity(intent);
            }
        });
    }
}
