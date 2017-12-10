package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2017/12/10.
 * @description 消息Bean
 */

public class MessageBean {

    /**
     *  自己发送的信息
     */
    public static final int SELF_SEND = 0;
    /**
     *  别人发送的信息
     */
    public static final int OTHER_SEND = 1;
    private String content;
    private int type;

    public MessageBean(String content,int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
