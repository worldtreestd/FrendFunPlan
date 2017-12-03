package com.legend.ffplan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legend.ffplan.R;
import com.legend.ffplan.common.viewimplement.ICommonView;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 添加计划页面
 */

public class JoinPlanFragment extends Fragment implements ICommonView{

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView ==  null) {
            mView = inflater.inflate(R.layout.joinplan_layout,container,false);
        }
        return mView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }
}
