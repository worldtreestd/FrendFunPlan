package com.legend.ffpmvp.circle.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.view.CircleConversationView;
import com.legend.ffpmvp.common.bean.MessageBean;
import com.legend.ffpmvp.common.view.CircleImageView;

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
        if (messageBean.getType() == CircleConversationView.SELF_SEND) {
            holder.user_name.setText(messageBean.getUser());
            Glide.with(mContext).load(messageBean.getUser_image_url())
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.me_image);
            holder.left_message.setVisibility(View.GONE);
            holder.right_message.setVisibility(View.VISIBLE);
            holder.right_text.setText(messageBean.getMessage().
                    subSequence(0,messageBean.getMessage().length()).toString());
        } else if (messageBean.getType() == CircleConversationView.OTHER_SEND){
            Glide.with(mContext).load(messageBean.getUser_image_url())
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.other_image);
            holder.other_name.setText(messageBean.getUser());
            holder.right_message.setVisibility(View.GONE);
            holder.left_message.setVisibility(View.VISIBLE);
            holder.left_text.setText(messageBean.getMessage().
                    subSequence(0,messageBean.getMessage().length()).toString());
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
        TextView user_name;
        TextView other_name;
        CircleImageView me_image;
        CircleImageView other_image;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            left_message = itemView.findViewById(R.id.left_layout);
            right_message = itemView.findViewById(R.id.right_layout);
            left_text = itemView.findViewById(R.id.left_msg);
            right_text = itemView.findViewById(R.id.right_msg);
            user_name = itemView.findViewById(R.id.me_name);
            other_name = itemView.findViewById(R.id.other_name);
            me_image = itemView.findViewById(R.id.me_image);
            other_image = itemView.findViewById(R.id.other_user_image);
        }
    }
    public CircleMessageAdapter(List<MessageBean> mMessageList) {
        this.mMessageList = mMessageList;
    }
}
