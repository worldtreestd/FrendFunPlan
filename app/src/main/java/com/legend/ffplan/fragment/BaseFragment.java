package com.legend.ffplan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Legend
 * @data by on 2018/1/13.
 * @description
 */

abstract public class BaseFragment extends Fragment {

    private View mView;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setResourceLayoutId(),container,false);
        mContext = getContext();
        initView();
        refreshData();
        initListener();
        return mView;
    }

    public abstract int setResourceLayoutId();

    public abstract void initView();

    public abstract void initListener();

    public abstract void refreshData();

    public <T extends View> T $(int id) {
        return (T)mView.findViewById(id);
    }

    public View getmView() {
        return mView;
    }
    public Context getmContext() {
        return mContext;
    }
}
