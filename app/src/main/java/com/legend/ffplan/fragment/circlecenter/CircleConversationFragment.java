package com.legend.ffplan.fragment.circlecenter;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.MessageBean;
import com.legend.ffplan.common.adapter.CircleMessageAdapter;
import com.legend.ffplan.common.viewimplement.ICommonView;

import java.util.ArrayList;
import java.util.List;

import me.james.biuedittext.BiuEditText;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈友会话
 */

public class CircleConversationFragment extends Fragment implements ICommonView{

    private View mView;
    private XRecyclerView mRecyclerView;
    private Button send_message;
    private BiuEditText input_message;
    private CircleMessageAdapter adapter;
    private List<MessageBean> messageBeanList = new ArrayList<>();
    private MessageBean[] messageList = new MessageBean[]{
            new MessageBean("what is your name？",MessageBean.OTHER_SEND),
            new MessageBean("My name is Legend.",MessageBean.SELF_SEND),
            new MessageBean("what are you doing？",MessageBean.OTHER_SEND),
            new MessageBean("I am Coding,and you?",MessageBean.SELF_SEND)};
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 防止软件盘挡住输入框
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (mView == null) {
            mView = inflater.inflate(R.layout.circle_conversation_layout,container,false);
        }
        initData();
        initView();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        send_message = mView.findViewById(R.id.send_message);
        input_message = mView.findViewById(R.id.input_message);
        adapter = new CircleMessageAdapter(messageBeanList);
        LinearLayoutManager manager = new LinearLayoutManager(mView.getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setPullRefreshEnabled(false);
    }

    @Override
    public void initListener() {
        adapter.notifyItemInserted(messageBeanList.size() - 1);
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = input_message.getText().toString();
                if (!"".equals(content)) {
                    MessageBean messageBean = new MessageBean(content,MessageBean.SELF_SEND);
                    messageBeanList.add(messageBean);
                    // 当有新消息时刷新并定位到最后一行
                    mRecyclerView.scrollToPosition(messageBeanList.size() - 1);
                    input_message.setText("");
                }
            }
        });
    }
    private void initData() {
        for (int i = 0;i < 5;i++) {
            messageBeanList.add(messageList[0]);
            messageBeanList.add(messageList[1]);
            messageBeanList.add(messageList[2]);
            messageBeanList.add(messageList[3]);
        }
    }
}
