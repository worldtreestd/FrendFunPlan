package com.legend.ffplan.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomePlanBean;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/5.
 * @description
 */

public class BackLogAdapter extends HomePlanAdapter {
    private Context mContext;
    public BackLogAdapter(List<HomePlanBean> PlanList) {
        super(PlanList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.backlog_list,parent,false);
        return new ViewHolder(view);
    }
}
