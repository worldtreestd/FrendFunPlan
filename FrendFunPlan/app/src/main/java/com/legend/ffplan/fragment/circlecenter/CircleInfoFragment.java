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
import com.legend.ffplan.common.viewimplement.ICommonView;

import static com.legend.ffplan.activity.CircleContentActivity.CIRCLE_ADDRESS;
import static com.legend.ffplan.activity.CircleContentActivity.CIRCLE_ADD_TIME;
import static com.legend.ffplan.activity.CircleContentActivity.CIRCLE_CREATED;
import static com.legend.ffplan.activity.CircleContentActivity.CIRCLE_ID;
import static com.legend.ffplan.activity.CircleContentActivity.CIRCLE_INTRODUCE;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子基本信息
 */

public class CircleInfoFragment extends Fragment implements ICommonView{

    private View mView;
    private TextView circle_desc_tv,circle_created_tv,circle_address_tv,
            circle_add_time_tv,circle_id_tv;
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
        int circle_id = intent.getIntExtra(CIRCLE_ID,000000);
        String circle_desc = intent.getStringExtra(CIRCLE_INTRODUCE);
        String circle_address = intent.getStringExtra(CIRCLE_ADDRESS);
        String circle_created = intent.getStringExtra(CIRCLE_CREATED);
        String circle_add_time = intent.getStringExtra(CIRCLE_ADD_TIME);

        circle_id_tv = mView.findViewById(R.id.circle_id);
        circle_add_time_tv = mView.findViewById(R.id.create_time);
        circle_address_tv = mView.findViewById(R.id.create_place);
        circle_created_tv = mView.findViewById(R.id.create_man);
        circle_desc_tv = mView.findViewById(R.id.circle_content_introduce);

        circle_id_tv.setText(""+circle_id);
        circle_add_time_tv.setText(circle_add_time.substring(0,10));
        circle_address_tv.setText(circle_address);
        circle_created_tv.setText(circle_created);
        circle_desc_tv.setText(circle_desc+"\n\n\n");

    }

    @Override
    public void initListener() {

    }
}
