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
    public static final String USER_NAME = "user_name";
    public static final String USER_IMAGE_URL = "user_image_url";
    private String content;
    private int type;
    private String user_image_url;
    private String user_name;

    public MessageBean(String content,int type) {
        this.content = content;
        this.type = type;
    }
    public MessageBean(String content,int type,String user_image_url,String user_name) {
        this.content = content;
        this.type = type;
        this.user_image_url = user_image_url;
        this.user_name = user_name;
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

    public String getUser_image_url() {
        return user_image_url;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
