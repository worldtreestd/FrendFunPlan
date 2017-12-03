package com.legend.ffplan.fragment.personalcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legend.ffplan.R;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description
 */

public class FinishedTaskFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.finishedtask_layout,container,false);
        }
        return mView;
    }
}
