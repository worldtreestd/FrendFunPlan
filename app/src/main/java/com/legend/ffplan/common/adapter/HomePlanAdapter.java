package com.legend.ffplan.common.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.HomePlanBean;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 主页计划列表适配器
 */

public class HomePlanAdapter extends RecyclerView.Adapter<HomePlanAdapter.ViewHolder>{

    private Context mContext;
    private List<HomePlanBean> mHomePlanList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView mPlan_From;
        TextView mPlan_StartTime;
        TextView mPlan_Content;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardview);
            mPlan_From = view.findViewById(R.id.plan_from);
            mPlan_StartTime = view.findViewById(R.id.plan_start_time);
            mPlan_Content = view.findViewById(R.id.plan_content);
        }
    }
    public HomePlanAdapter(List<HomePlanBean> PlanList) {
        this.mHomePlanList = PlanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomePlanBean mPlanList = mHomePlanList.get(position);
        holder.mPlan_From.setText(" From："+mPlanList.getFrom());
        holder.mPlan_Content.setText(mPlanList.getContent());
        holder.mPlan_StartTime.setText(" "+mPlanList.getStartTime());
    }

    @Override
    public int getItemCount() {
        return mHomePlanList.size();
    }
}
