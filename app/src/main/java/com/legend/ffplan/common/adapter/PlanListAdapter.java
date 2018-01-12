package com.legend.ffplan.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.legend.ffplan.R;
import com.legend.ffplan.activity.PlanContentActivity;
import com.legend.ffplan.common.Bean.HomePlanBean;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 计划列表适配器
 */

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewHolder>{

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
    public PlanListAdapter(List<HomePlanBean> PlanList) {
        this.mHomePlanList = PlanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_plan_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition()-1;
                HomePlanBean mPlanBeans = mHomePlanList.get(position);
                Intent intent = new Intent(mContext,PlanContentActivity.class);
                intent.putExtra(PlanContentActivity.PLAN_ID,mPlanBeans.getId());
                intent.putExtra(PlanContentActivity.PLAN_START_TIME,mPlanBeans.getAdd_time().replace("T"," ").substring(0,19));
                intent.putExtra(PlanContentActivity.PLAN_CONTENT,mPlanBeans.getContent());
                intent.putExtra(PlanContentActivity.PLAN_FROM,mPlanBeans.getFrom_circle_name());
                intent.putExtra(PlanContentActivity.PLAN_ADDRESS,mPlanBeans.getAddress());
                intent.putExtra(PlanContentActivity.PLAN_END_TIME,mPlanBeans.getEnd_time().replace("-","/").substring(0,10));
                intent.putExtra(PlanContentActivity.PLAN_CREATED,mPlanBeans.getUser());
                intent.putExtra(PlanContentActivity.PLAN_PART_NUM,mPlanBeans.getUsers_num());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomePlanBean mPlanList = mHomePlanList.get(position);
        holder.mPlan_From.setText(" From："+mPlanList.getFrom_circle_name());
        holder.mPlan_Content.setText(mPlanList.getContent());
        holder.mPlan_StartTime.setText(" "+mPlanList.getAdd_time().replace("T","-").substring(0,19));
    }

    @Override
    public int getItemCount() {
        return mHomePlanList.size();
    }
}
