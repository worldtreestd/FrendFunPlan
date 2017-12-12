package com.legend.ffplan.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.legend.ffplan.R;
import com.legend.ffplan.activity.CircleContentActivity;
import com.legend.ffplan.common.Bean.HomeCircleBean;
import com.legend.ffplan.fragment.PersonalCenterFragment;
import com.legend.ffplan.fragment.circlecenter.CircleConversationFragment;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/4.
 * @description 圈子列表适配器
 */

public class CircleListAdapter extends RecyclerView.Adapter<CircleListAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeCircleBean> mMyCircleBeanList;

    public CircleListAdapter(List<HomeCircleBean> mMyCircleBeanList) {
        this.mMyCircleBeanList = mMyCircleBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.circle_list,null);
        final ViewHolder mViewHolder = new ViewHolder(view);
        mViewHolder.circle_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 默认顶部item占一个位置
                int position = mViewHolder.getAdapterPosition()-1;

                HomeCircleBean circle = mMyCircleBeanList.get(position);
                Intent intent = new Intent(mContext, CircleContentActivity.class);
                String username = intent.getStringExtra("NICKNAME");
                intent.putExtra(CircleContentActivity.CIRCLE_NAME,circle.getName());
                intent.putExtra(CircleContentActivity.CIRCLE_IMAGE_URL,circle.getImage());
                intent.putExtra(CircleContentActivity.CIRCLE_INTRODUCE,circle.getDesc());
                intent.putExtra(CircleContentActivity.CIRCLE_ADDRESS,circle.getAddress());
                intent.putExtra(CircleContentActivity.CIRCLE_CREATED,circle.getUser());
                intent.putExtra(CircleContentActivity.CIRCLE_ID,circle.getId());
                intent.putExtra(CircleContentActivity.CIRCLE_ADD_TIME,circle.getAdd_time());
                intent.putExtra(CircleConversationFragment.USER_NAME, PersonalCenterFragment.user_nick_name);
                intent.putExtra(CircleConversationFragment.USER_IMAGE_URL,PersonalCenterFragment.user_image_url);
                mContext.startActivity(intent);
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCircleBean circleBean = mMyCircleBeanList.get(position);
        Glide.with(mContext).load(circleBean.getImage()).into(holder.circleTextImageView);
        holder.circle_name.setText(circleBean.getName());
        holder.circle_introduce.setText(circleBean.getDesc());
    }

    @Override
    public int getItemCount() {
        return mMyCircleBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleTextImageView circleTextImageView;
        TextView circle_name;
        TextView circle_introduce;
        RelativeLayout circle_item;
        public ViewHolder(View view) {
            super(view);
            circle_item = view.findViewById(R.id.circle_item);
            circleTextImageView = view.findViewById(R.id.create_circle);
            circle_name = view.findViewById(R.id.circle_name);
            circle_introduce = view.findViewById(R.id.circle_introduce);
        }
    }
}
