package com.legend.ffpmvp.main.personalcenter.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/14.
 * @description 已完任务适配器-时间轴
 */

public class FinishedTaskAdapter extends XRecyclerView.Adapter<FinishedTaskAdapter.ViewHolder>{

    private Context mContext;
    private List<HomePlanBean> finishedTaskBeans;

    public FinishedTaskAdapter(List<HomePlanBean> taskBeanList) {
        this.finishedTaskBeans = taskBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View mView = LayoutInflater.from(mContext).inflate(R.layout.finished_task_item,null);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomePlanBean taskBean = finishedTaskBeans.get(position);
        holder.finished_plan_time.setText(taskBean.getAdd_time().substring(11,16));
        holder.finished_plan_date.setText(taskBean.getAdd_time().substring(0,10));
        holder.finished_plan_content.setText(taskBean.getContent());
    }

    @Override
    public int getItemCount() {
        return finishedTaskBeans.size();
    }

    static class ViewHolder extends XRecyclerView.ViewHolder {

        TextView finished_plan_date;
        TextView finished_plan_time;
        TextView finished_plan_content;

        public ViewHolder(View itemView) {
            super(itemView);
            finished_plan_date = itemView.findViewById(R.id.plan_finished_date);
            finished_plan_time = itemView.findViewById(R.id.plan_finished_time);
            finished_plan_content = itemView.findViewById(R.id.plan_finished_content);
        }
    }
}
