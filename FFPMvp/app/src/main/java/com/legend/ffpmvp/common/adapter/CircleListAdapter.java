package com.legend.ffpmvp.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.view.CircleContentView;
import com.legend.ffpmvp.circle.view.CircleConversationView;
import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.view.banner.BannerViewAdapter;
import com.legend.ffpmvp.main.view.MainActivity;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/4.
 * @description 圈子列表适配器
 */

public class CircleListAdapter extends RecyclerView.Adapter<CircleListAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeCircleBean> mMyCircleBeanList;
    private int type;

    public CircleListAdapter(List<HomeCircleBean> mMyCircleBeanList) {
        this.mMyCircleBeanList = mMyCircleBeanList;
    }

    public CircleListAdapter(List<HomeCircleBean> mMyCircleBeanList,int type) {
        this.mMyCircleBeanList = mMyCircleBeanList;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.circle_list_item,null);
        final ViewHolder mViewHolder = new ViewHolder(view);
        mViewHolder.circle_item.setOnClickListener(view1 -> {
            // 默认顶部item占一个位置
            int position = mViewHolder.getAdapterPosition()-2;
            if (position < 0 || type == 0) {
                position = mViewHolder.getAdapterPosition()-1;
            }
            HomeCircleBean bean = mMyCircleBeanList.get(position);
            Intent intent = BannerViewAdapter.createIntent(mContext, bean);
            Pair<View, String> imagePair = Pair.create((View)mViewHolder.circleTextImageView, "circle_image");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) mContext, imagePair);
            mContext.startActivity(intent,options.toBundle());

        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCircleBean circleBean = mMyCircleBeanList.get(position);
        Glide.with(mContext)
                .load(circleBean.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .error(R.drawable.ic_launcher_background)
                .into(holder.circleTextImageView);
        holder.circle_name.setText(circleBean.getName());
        holder.circle_introduce.setText(circleBean.getDesc());
        holder.watch_num.setText("围观"+circleBean.getWatch_num()+"次");
    }

    @Override
    public int getItemCount() {
        return mMyCircleBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView circleTextImageView;
        TextView circle_name;
        TextView circle_introduce;
        CardView circle_item;
        AppCompatTextView watch_num;

        public ViewHolder(View view) {
            super(view);
            circle_item = view.findViewById(R.id.circle_item);
            circleTextImageView = view.findViewById(R.id.create_circle);
            circle_name = view.findViewById(R.id.circle_name);
            circle_introduce = view.findViewById(R.id.circle_introduce);
            watch_num = view.findViewById(R.id.watch_num_tv);
        }
    }
}
