package com.legend.ffpmvp.common.bean;

/**
 * @author Legend
 * @data by on 2018/1/5.
 * @description
 */

public class HomePlanBean {

    /**
     * from_circle_name : 世界树
     * user : admin
     * end_time : 2017-12-30T09:51:00
     * content : 胜多负少爽肤水看出来是否深V看DVD没v啥都没出大V次幂多少v模式的方式递归漏电开关2数据访问
     * address : 望城县
     * users_num : 3
     * add_time : 2017-12-30T09:51:00
     */

    private int id;
    private String from_circle_name;
    private int from_circle;
    private String user;
    private String end_time;
    private String content;
    private String address;
    private int users_num;
    private String add_time;


    public HomePlanBean(String startTime, String content, String from) {
        this.add_time = startTime;
        this.content = content;
        this.from_circle_name = from;
    }
    public HomePlanBean(int id,String startTime, String content, String from,String user,String address,int users_num,String end_time,int from_circle) {
        this.id = id;
        this.add_time = startTime;
        this.content = content;
        this.from_circle_name = from;
        this.user = user;
        this.address = address;
        this.users_num = users_num;
        this.end_time = end_time;
        this.from_circle = from_circle;
    }

    public String getFrom_circle_name() {
        return from_circle_name;
    }

    public void setFrom_circle_name(String from_circle_name) {
        this.from_circle_name = from_circle_name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUsers_num() {
        return users_num;
    }

    public void setUsers_num(int users_num) {
        this.users_num = users_num;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_circle() {
        return from_circle;
    }

    public void setFrom_circle(int from_circle) {
        this.from_circle = from_circle;
    }
}
