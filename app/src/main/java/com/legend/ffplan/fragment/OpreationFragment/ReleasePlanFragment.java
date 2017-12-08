package com.legend.ffplan.fragment.OpreationFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.legend.ffplan.R;
import com.legend.ffplan.common.viewimplement.ICommonView;

import me.james.biuedittext.BiuEditText;

/**
 * @author Legend
 * @data by on 2017/12/4.
 * @description 发布一条计划
 */

public class ReleasePlanFragment extends Fragment implements ICommonView {

    private View mView;
    private BiuEditText set_date_end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.release_plan_layout,container,false);
        }
        initView();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        set_date_end = mView.findViewById(R.id.date_end);
    }

    @Override
    public void initListener() {
        set_date_end.setFocusable(false);
        set_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(mView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        set_date_end.setText(String.format("%d——%d——%d",i,i1,i2));
                    }
                },2017,11,1).show();
            }
        });
    }
}
