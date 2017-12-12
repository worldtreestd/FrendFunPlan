package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2017/12/4.
 * @description 圈子Bean
 */

public class MyCircleBean {
    /**
     *  圈子头像
     */
    private int imageId;
    /**
     * 圈子名称
     */
    private String name;
    /**
     *  圈子地点
     */
    private String address;
    /**
     *  创建人
     */
    private String create_man;
    /**
     *  创建时间
     */
    private String create_time;
    /**
     *  圈子介绍
     */
    private String introduce;
    public MyCircleBean(int imageId, String name, String introduce) {
        this.name = name;
        this.imageId= imageId;
        this.introduce = introduce;
    }
    public MyCircleBean(String name, String introduce) {
        this.name = name;
        this.imageId = imageId;
        this.introduce = introduce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageView(int imageId) {
        this.imageId = imageId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_man() {
        return create_man;
    }

    public void setCreate_man(String create_man) {
        this.create_man = create_man;
    }
}
