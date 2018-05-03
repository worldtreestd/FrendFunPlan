package com.legend.ffpmvp.circle.contract;

import com.legend.ffpmvp.common.bean.MessageBean;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/31.
 * @description
 */

public class CircleMessageContract {


    public interface ICircleConversationModel {
        void getMessage(int id,MessageCallBack callBack);
        void sendMessage(String...params);
    }

    public interface ICircleConversationPresenter {
        void getMessage(int id);
        void sendMessage(String...params);
        int getBeanSize();
    }

    public interface ICircleConversationView {
        String getUserName();
        String getUserId();
    }

    public interface MessageCallBack {

        void onSuccess(List<MessageBean> tList);
        void onFailure();
    }
}
