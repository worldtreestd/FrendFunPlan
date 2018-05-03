package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2017/12/10.
 * @description 消息Bean
 */

public class MessageBean {

    /**
     * circle : 100002
     * user : admin
     * message : 超时代
     * type : 0
     * add_time : 2018-01-10T11:45:33.582194
     * user_image_url :
     */

    private int id;
    private int circle;
    private String user;
    private String message;
    private int type;
    private String add_time;
    private String user_image_url;

    public MessageBean(String message,int type,String user_image_url,String user_name) {
        this.message = message;
        this.user_image_url = user_image_url;
        this.user = user_name;
        this.type= type;
    }
    public MessageBean(int circle_id,String message) {
        this.circle = circle_id;
        this.message = message;
    }

    public int getCircle() {
        return circle;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUser_image_url() {
        return user_image_url;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
