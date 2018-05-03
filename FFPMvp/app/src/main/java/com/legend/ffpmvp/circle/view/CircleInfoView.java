package com.legend.ffpmvp.circle.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.view.BaseFragment;

import static com.legend.ffpmvp.circle.view.CircleContentView.CIRCLE_ADDRESS;
import static com.legend.ffpmvp.circle.view.CircleContentView.CIRCLE_ADD_TIME;
import static com.legend.ffpmvp.circle.view.CircleContentView.CIRCLE_CREATED;
import static com.legend.ffpmvp.circle.view.CircleContentView.CIRCLE_ID;
import static com.legend.ffpmvp.circle.view.CircleContentView.CIRCLE_INTRODUCE;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class CircleInfoView extends BaseFragment {

    private View mView;
    private TextView circle_desc_tv,circle_created_tv,circle_address_tv,
            circle_add_time_tv,circle_id_tv;

    @Override
    public int setResourceLayoutId() {
        return R.layout.circle_info_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return 0;
    }

    @Override
    public void initView() {
        mView = getmView();
        Intent intent = getActivity().getIntent();
        int circle_id = intent.getIntExtra(CIRCLE_ID,000000);
        String circle_desc = intent.getStringExtra(CIRCLE_INTRODUCE);
        String circle_address = intent.getStringExtra(CIRCLE_ADDRESS);
        String circle_created = intent.getStringExtra(CIRCLE_CREATED);
        String circle_add_time = intent.getStringExtra(CIRCLE_ADD_TIME);

        circle_id_tv = $(R.id.circle_id);
        circle_add_time_tv = $(R.id.create_time);
        circle_address_tv = $(R.id.create_place);
        circle_created_tv = $(R.id.create_man);
        circle_desc_tv = $(R.id.circle_content_introduce);

        circle_id_tv.setText(""+circle_id);
        circle_add_time_tv.setText(circle_add_time.substring(0,10));
        circle_address_tv.setText(circle_address);
        circle_created_tv.setText(circle_created);
        circle_desc_tv.setText(circle_desc+"\n\n\n");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void refreshData() {

    }
}
