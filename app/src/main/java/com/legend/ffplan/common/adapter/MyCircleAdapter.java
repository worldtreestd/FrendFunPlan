package com.legend.ffplan.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.MyCircleBean;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/4.
 * @description
 */

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.ViewHolder> {

    private Context mContext;
    private List<MyCircleBean> mMyCircleBeanList;

    public MyCircleAdapter(List<MyCircleBean> mMyCircleBeanList) {
        this.mMyCircleBeanList = mMyCircleBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_circle,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyCircleBean circleBean = mMyCircleBeanList.get(position);
        holder.circleTextImageView.setText(circleBean.getName());
    }

    @Override
    public int getItemCount() {
        return mMyCircleBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleTextImageView circleTextImageView;
        public ViewHolder(View view) {
            super(view);
            circleTextImageView = view.findViewById(R.id.create_circle);
        }
    }
}
