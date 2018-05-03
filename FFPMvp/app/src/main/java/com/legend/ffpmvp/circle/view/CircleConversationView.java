package com.legend.ffpmvp.circle.view;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.contract.CircleMessageContract;
import com.legend.ffpmvp.circle.model.CircleConversationModelImpl;
import com.legend.ffpmvp.circle.presenter.CircleConversationPresenter;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseFragment;

import java.lang.ref.WeakReference;

import me.james.biuedittext.BiuEditText;

import static com.legend.ffpmvp.circle.presenter.CircleConversationPresenter.adapter;

/**
 * @author Legend
 * @data by on 2018/1/31.
 * @description
 */

public class CircleConversationView extends BaseFragment implements CircleMessageContract.ICircleConversationView {

    private View mView;
    /**
     *  发送的信息
     */
    public static final int SELF_SEND = 1;
    /**
     *  接收的信息
     */
    public static final int OTHER_SEND = 0;
    public static final String USER_NAME = "user_name";
    public static final String USER_ID = "user_open_id";
    public static final String USER_IMAGE_URL = "user_image_url";
    private XRecyclerView mRecyclerView;
    private Button send_message;
    private BiuEditText input_message;
    private String username,user_id;
    private String user_image_url;
    private String content="";
    private int id;
    private CircleMessageContract.ICircleConversationModel model;
    private CircleMessageContract.ICircleConversationPresenter presenter;

    @Override
    public int setResourceLayoutId() {
        return R.layout.circle_conversation_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return R.id.mRecyclerView;
    }

    @Override
    public void initView() {
        model = new CircleConversationModelImpl();
        WeakReference<CircleConversationView> reference = new WeakReference<>(this);
        presenter = new CircleConversationPresenter(model,reference.get());
        // 防止键盘挡住输入框
        mView = getmView();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        send_message = mView.findViewById(R.id.send_message);
        input_message = mView.findViewById(R.id.input_message);

        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra(CircleContentView.CIRCLE_ID, 100001);
        username = intent.getStringExtra(USER_NAME);
        user_id = intent.getStringExtra(USER_ID);
        user_image_url = intent.getStringExtra(USER_IMAGE_URL);
    }

    @Override
    public void initListener() {
        mRecyclerView = getmRecyclerView();
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyItemChanged(presenter.getBeanSize() - 1);
        // 当有新消息时刷新并定位到最后一行
        mRecyclerView.scrollToPosition(presenter.getBeanSize());
        send_message.setOnClickListener(view -> {
            content = input_message.getText().subSequence(0,input_message.getText().length()).toString();
            Log.d("mesageimage",content.toString());
            presenter.sendMessage(String.valueOf(id),content,user_image_url);
            // 当有新消息时刷新并定位到最后一行
            mRecyclerView.scrollToPosition(presenter.getBeanSize());
            input_message.setText("");
            if (content.length() != 0) {
                new Handler().postDelayed(() -> presenter.getMessage(id),1000);
            }
        });
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public void refreshData() {
        if (ToastUtils.checkNetState(MyApplication.getInstance())) {
            new Handler().postDelayed(() -> {
                presenter.getMessage(id);
                mRecyclerView.refreshComplete();
            },500);
        } else {
            ToastUtils.showToast(mView.getContext(),"您的网络连接有误 请检查一下连接状态");
        }
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getUserId() {
        return user_id;
    }
}
