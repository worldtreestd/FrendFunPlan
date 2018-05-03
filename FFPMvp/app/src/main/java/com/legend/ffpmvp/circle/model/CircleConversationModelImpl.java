package com.legend.ffpmvp.circle.model;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.circle.contract.CircleMessageContract;
import com.legend.ffpmvp.common.bean.MessageBean;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import static com.legend.ffpmvp.common.bean.Status.UNKNOW_ERROR;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;

/**
 * @author Legend
 * @data by on 2018/1/31.
 * @description
 */

public class CircleConversationModelImpl implements CircleMessageContract.ICircleConversationModel {

    private CircleMessageContract.MessageCallBack callBack;
    private List<MessageBean> messageList = new ArrayList<>();

    @Override
    public void getMessage(int id, CircleMessageContract.MessageCallBack callBack) {
        this.callBack = callBack;
        new ReceiveMessageAsyncTask().execute(ApiUtils.MESSAGES+"?search="+id);
    }

    @Override
    public void sendMessage(String... params) {
        new SendMessageAsyncTask().execute(ApiUtils.MESSAGES, params[0],params[1],params[2]);
    }

    /**
     *  接收消息
     */
    class ReceiveMessageAsyncTask extends AsyncTask<String,Void,List<MessageBean>> {

        @Override
        protected List<MessageBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            Log.d("messagecode",response.getCode()+"");
            if (response.getCode() != UN_LOGIN && response.getCode() != UNKNOW_ERROR ) {
                String data = response.getData().toString();
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    messageList =
                            gson.fromJson(data,new TypeToken<List<MessageBean>>(){}.getType());
                }
            }
            return messageList;
        }

        @Override
        protected void onPostExecute(List<MessageBean> messageBeans) {
            super.onPostExecute(messageBeans);
            if (messageBeans != null && messageBeans.size() > 0) {
                callBack.onSuccess(messageBeans);
            } else {
                callBack.onFailure();
            }
        }
    }

    /**
     *  发送消息
     */
    class SendMessageAsyncTask  extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("circle",strings[1]);
            request.setBody("message",strings[2]);
            request.setBody("user_image_url",strings[3]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.post(request);
            return response;
        }
    }
}
