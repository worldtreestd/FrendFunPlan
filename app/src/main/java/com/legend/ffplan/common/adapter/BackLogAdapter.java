package com.legend.ffplan.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.legend.ffplan.R;
import com.legend.ffplan.activity.PlanContentActivity;
import com.legend.ffplan.common.Bean.HomePlanBean;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.fragment.personalcenter.BackLogFragment;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/5.
 * @description 待办事项适配器
 */

public class BackLogAdapter extends PlanListAdapter {
    private Context mContext;
    private List<HomePlanBean> mHomePlanList;
    private Button delete_plan_btn;
    private  BackLogFragment backLogFragment;
    public BackLogAdapter(List<HomePlanBean> PlanList) {
        super(PlanList);
        this.mHomePlanList = PlanList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.backlog_list,parent,false);
        delete_plan_btn = view.findViewById(R.id.delete_plan);
        final ViewHolder holder = new ViewHolder(view);

        delete_plan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition()-1;
                HomePlanBean mPlanBeans = mHomePlanList.get(position);
                new OperatePlanAsyncTask().execute(ApiUtils.PARTPLANS+mPlanBeans.getId()+"/");
            }
        });
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
                intent.putExtra(BackLogFragment.STATUS,"已加入");
                mContext.startActivity(intent);
            }
        });
        return holder;
    }
    class OperatePlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.delete(request);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == 204) {
                ToastUtils.showToast(MyApplication.getInstance(),"您已成功删除该条计划");
            } else if (iResponse.getCode() == 404) {
                ToastUtils.showToast(MyApplication.getInstance(),"当前计划已经被删除 请尝试刷新");
            } else {
                ToastUtils.showToast(MyApplication.getInstance(),"未知的错误");
            }
        }
    }
}
