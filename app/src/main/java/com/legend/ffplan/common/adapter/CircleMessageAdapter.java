package com.legend.ffplan.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.MessageBean;

import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/10.
 * @description 圈子消息交流圈适配器
 */

public class CircleMessageAdapter extends XRecyclerView.Adapter<CircleMessageAdapter.ViewHolder>{

    private Context mContext;
    private List<MessageBean> mMessageList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageBean messageBean = mMessageList.get(position);
        if (messageBean.getType() == MessageBean.SELF_SEND) {
            holder.left_message.setVisibility(View.GONE);
            holder.right_message.setVisibility(View.VISIBLE);
            holder.right_text.setText(messageBean.getContent());
        } else if (messageBean.getType() == messageBean.OTHER_SEND) {
            holder.right_message.setVisibility(View.GONE);
            holder.left_message.setVisibility(View.VISIBLE);
            holder.left_text.setText(messageBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    static class ViewHolder extends XRecyclerView.ViewHolder {

        LinearLayout left_message;
        RelativeLayout right_message;
        TextView left_text;
        TextView right_text;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            left_message = itemView.findViewById(R.id.left_layout);
            right_message = itemView.findViewById(R.id.right_layout);
            left_text = itemView.findViewById(R.id.left_msg);
            right_text = itemView.findViewById(R.id.right_msg);
        }
    }
    public CircleMessageAdapter(List<MessageBean> mMessageList) {
        this.mMessageList = mMessageList;
    }
}
