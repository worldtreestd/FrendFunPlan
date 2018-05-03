package com.legend.ffpmvp.circle.presenter;

import android.text.TextUtils;

import com.legend.ffpmvp.circle.contract.CircleMessageContract;
import com.legend.ffpmvp.circle.model.adapter.CircleMessageAdapter;
import com.legend.ffpmvp.common.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

import static com.legend.ffpmvp.circle.view.CircleConversationView.OTHER_SEND;
import static com.legend.ffpmvp.circle.view.CircleConversationView.SELF_SEND;

/**
 * @author Legend
 * @data by on 2018/1/31.
 * @description
 */

public class CircleConversationPresenter implements CircleMessageContract.ICircleConversationPresenter {

    public static CircleMessageAdapter adapter;
    private CircleMessageContract.ICircleConversationModel model;
    private CircleMessageContract.ICircleConversationView view;
    private List<MessageBean> messageBeanList;
    private MessageBean messageBean;

    public CircleConversationPresenter(CircleMessageContract.ICircleConversationModel model, CircleMessageContract.ICircleConversationView view) {
        this.model = model;
        this.view = view;
        messageBeanList = new ArrayList<>();
        adapter = new CircleMessageAdapter(messageBeanList);
    }

    @Override
    public void getMessage(int id) {
        messageBeanList.clear();
        model.getMessage(id, new CircleMessageContract.MessageCallBack() {
            @Override
            public void onSuccess(List<MessageBean> list) {
                for (MessageBean message: list) {
                    if (!TextUtils.isEmpty(view.getUserName())) {
                        if (view.getUserId().equals(message.getOpen_id())) {
                            messageBean =
                                    new MessageBean(message.getMessage(),SELF_SEND,message.getUser_image_url(),view.getUserName());
                        } else {
                            messageBean =
                                    new MessageBean(message.getMessage(),OTHER_SEND,message.getUser_image_url(),message.getUser());
                        }
                        messageBeanList.add(messageBean);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void sendMessage(String... params) {
        if (!"".equals(params[1])) {
            MessageBean messageBean = new MessageBean(params[1],SELF_SEND,params[2],view.getUserName());
            messageBeanList.add(messageBean);
            model.sendMessage(params);
        }
    }

    @Override
    public int getBeanSize() {
        return messageBeanList.size();
    }
}
