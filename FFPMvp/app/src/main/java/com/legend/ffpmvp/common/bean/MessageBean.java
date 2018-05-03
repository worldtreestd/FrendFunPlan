package com.legend.ffpmvp.common.bean;

/**
 * @author Legend
 * @data by on 2017/12/10.
 * @description 消息Bean
 */

public class MessageBean {


    /**
     * circle : 100063
     * user : 丶legend
     * open_id : F1DDA0DA3A53C0DC3C10ACF4687BE164
     * message : 可以发表情了😳😬😬😬
     * type : 0
     * add_time : 2018-03-16T08:53:27.131345
     * user_image_url : http://thirdqq.qlogo.cn/qqapp/1106491809/F1DDA0DA3A53C0DC3C10ACF4687BE164/100
     */

    private int circle;
    private String user;
    private String open_id;
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

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
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
}
