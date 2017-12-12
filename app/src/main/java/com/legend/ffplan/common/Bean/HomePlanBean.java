package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2017/11/29.
 * @description 计划Bean
 */

public class HomePlanBean {
    /**
     *  开始时间
     */
    private String startTime;
    /**
     *  结束时间
     */
    private String endTime;
    /**
     *  计划详情
     */
    private String content;
    /**
     *  计划所属圈子
     */
    private String from;
    /**
     *  创建人
     */
    private String create_man;
    /**
     *  当前加入人数
     */
    private int current_person_count;

    public HomePlanBean(String startTime, String content, String from) {
        this.startTime = startTime;
        this.content = content;
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCreate_man() {
        return create_man;
    }

    public void setCreate_man(String create_man) {
        this.create_man = create_man;
    }

    public int getCurrent_person_count() {
        return current_person_count;
    }

    public void setCurrent_person_count(int current_person_count) {
        this.current_person_count = current_person_count;
    }
}
