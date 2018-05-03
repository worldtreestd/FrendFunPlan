package com.legend.ffpmvp.main.personalcenter.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.adapter.PlanListAdapter;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.plan.view.PlanContentView;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class BackLogAdapter extends PlanListAdapter {
    private Context mContext;
    private List<HomePlanBean> mHomePlanList;
    private Button delete_plan_btn;
    private Button finished_plan_btn;
    private OnItemClickListener mOnItemClickListener;

    public BackLogAdapter(List<HomePlanBean> PlanList) {
        super(PlanList);
        this.mHomePlanList = PlanList;
    }
    public interface OnItemClickListener {

        void onUpdateClickListener(View view,int position,int id);

        void onDeleteClickListener(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.backlog_list_item,parent,false);
        delete_plan_btn = view.findViewById(R.id.delete_plan);
        finished_plan_btn = view.findViewById(R.id.finished_btn);
        final ViewHolder holder = new ViewHolder(view);

        finished_plan_btn.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition()-1;
            HomePlanBean bean = mHomePlanList.get(position);
            mOnItemClickListener.onUpdateClickListener(view1,bean.getId(),bean.getFrom_circle());
        });
        delete_plan_btn.setOnClickListener(view12 -> {
            int position = holder.getAdapterPosition()-1;
            HomePlanBean bean = mHomePlanList.get(position);
            mOnItemClickListener.onDeleteClickListener(view12,bean.getId());
        });
        holder.cardView.setOnClickListener(view13 -> {
            int position = holder.getAdapterPosition()-1;
            HomePlanBean bean = mHomePlanList.get(position);
            Intent intent = createIntent(mContext, bean);
            mContext.startActivity(intent);
        });
        return holder;
    }
    
    public static Intent createIntent(Context context, HomePlanBean bean) {
        final Intent intent = new Intent(context,PlanContentView.class);
        intent.putExtra(PlanContentView.PLAN_ID,bean.getId());
        intent.putExtra(PlanContentView.PLAN_START_TIME,bean.getAdd_time().replace("T"," ").substring(0,19));
        intent.putExtra(PlanContentView.PLAN_CONTENT,bean.getContent());
        intent.putExtra(PlanContentView.PLAN_FROM,bean.getFrom_circle_name());
        intent.putExtra(PlanContentView.PLAN_ADDRESS,bean.getAddress());
        intent.putExtra(PlanContentView.PLAN_END_TIME,bean.getEnd_time().replace("-","/").substring(0,10));
        intent.putExtra(PlanContentView.PLAN_CREATED,bean.getUser());
        intent.putExtra(PlanContentView.PLAN_PART_NUM,bean.getUsers_num());
        return intent;
    }
}