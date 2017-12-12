package com.legend.ffplan.fragment.circlecenter;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffplan.R;
import com.legend.ffplan.activity.CircleContentActivity;
import com.legend.ffplan.common.Bean.MessageBean;
import com.legend.ffplan.common.adapter.CircleMessageAdapter;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
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
    /**
     *  发送的信息
     */
    public static final int SELF_SEND = 1;
    /**
     *  接收的信息
     */
    public static final int OTHER_SEND = 0;
    public static final String USER_NAME = "user_name";
    public static final String USER_IMAGE_URL = "user_image_url";
    private XRecyclerView mRecyclerView;
    private Button send_message;
    private BiuEditText input_message;
    private CircleMessageAdapter adapter;
    private List<MessageBean> messageBeanList = new ArrayList<>();
    private MessageBean messageBean;
    private String username;
    private String user_image_url;
    private String content;
    private int id;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 防止软件盘挡住输入框
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (mView == null) {
            mView = inflater.inflate(R.layout.circle_conversation_layout,container,false);
        }
        initView();
        initData();
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
                content = input_message.getText().toString();
                new SendMessageAsyncTask().execute(ApiUtils.MESSAGES);
                if (!"".equals(content)) {
                    MessageBean messageBean = new MessageBean(content,SELF_SEND,user_image_url,username);
                    messageBeanList.add(messageBean);
                    // 当有新消息时刷新并定位到最后一行
                    mRecyclerView.scrollToPosition(messageBeanList.size() - 1);
                    input_message.setText("");
                }
            }
        });
    }
    private void initData() {
        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra(CircleContentActivity.CIRCLE_ID, 100001);
        username = intent.getStringExtra(CircleConversationFragment.USER_NAME);
        user_image_url = intent.getStringExtra(CircleConversationFragment.USER_IMAGE_URL);
        new ReceiveMessageAsyncTask().execute(ApiUtils.MESSAGES+"?search="+id);
    }
    /**
     *  发送消息
     */
    class SendMessageAsyncTask  extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("circle",id);
            request.setBody("message",content);
            request.setBody("user_image_url",user_image_url);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.post(request);
            return response;
        }
    }

    /**
     *  接收消息
     */
    class ReceiveMessageAsyncTask extends AsyncTask<String,Void,List<MessageBean>> {

        @Override
        protected List<MessageBean> doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            String data = response.getData().toString();
            Gson gson = new Gson();
            List<MessageBean> messageList =
                    gson.fromJson(data,new TypeToken<List<MessageBean>>(){}.getType());
            return messageList;
        }

        @Override
        protected void onPostExecute(List<MessageBean> messageBeans) {
            super.onPostExecute(messageBeans);
            messageBeanList.clear();
            for (MessageBean message : messageBeans) {
                if (username.toString().equals(message.getUser())) {
                    messageBean =
                            new MessageBean(message.getMessage(),SELF_SEND,message.getUser_image_url(),message.getUser());
                } else {
                    messageBean =
                            new MessageBean(message.getMessage(),OTHER_SEND,message.getUser_image_url(),message.getUser());
                }
                messageBeanList.add(messageBean);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
