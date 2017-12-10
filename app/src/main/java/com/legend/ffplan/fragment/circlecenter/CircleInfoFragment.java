package com.legend.ffplan.fragment.circlecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legend.ffplan.R;
import com.legend.ffplan.activity.CircleContentActivity;
import com.legend.ffplan.common.viewimplement.ICommonView;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子基本信息
 */

public class CircleInfoFragment extends Fragment implements ICommonView{

    private View mView;
    private TextView circle_content_introduce;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.circle_info_layout,container,false);
        }
        initView();
        return mView;
    }

    @Override
    public void initView() {
        Intent intent = getActivity().getIntent();
        String circle_introduce = intent.getStringExtra(CircleContentActivity.CIRCLE_INTRODUCE);
        circle_content_introduce = mView.findViewById(R.id.circle_content_introduce);
        circle_content_introduce.setText(circle_introduce+"\n\n\n\n\n");
    }

    @Override
    public void initListener() {

    }
}
