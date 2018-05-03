package com.legend.ffpmvp.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.main.personalcenter.model.BackLogAdapter;
import com.legend.ffpmvp.plan.view.PlanContentView;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 计划列表适配器
 */

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewHolder>{

    private Context mContext;
    private List<HomePlanBean> mHomePlanList;
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        TextView mPlan_From;
        TextView mPlan_StartTime;
        TextView mPlan_Content;
        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardview);
            mPlan_From = view.findViewById(R.id.plan_from);
            mPlan_StartTime = view.findViewById(R.id.plan_start_time);
            mPlan_Content = view.findViewById(R.id.plan_content);
        }
    }
    public PlanListAdapter(List<HomePlanBean> PlanList) {
        this.mHomePlanList = PlanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.plan_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition()-1;
            HomePlanBean bean = mHomePlanList.get(position);
            Intent intent = BackLogAdapter.createIntent(mContext, bean);
            mContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomePlanBean mPlanList = mHomePlanList.get(position);
        holder.mPlan_From.setText(mPlanList.getFrom_circle_name());
        holder.mPlan_Content.setText(mPlanList.getContent());
        holder.mPlan_StartTime.setText(" "+mPlanList.getAdd_time().replace("T","-").substring(0,19));
    }

    @Override
    public int getItemCount() {
        return mHomePlanList.size();
    }
}
