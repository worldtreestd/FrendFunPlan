package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2017/12/4.
 * @description
 */

public class MyCircleBean {
    private int imageId;
    private String name;
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
}
