package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2017/11/29.
 * @description
 */

public class HomePlanBean {
    private String startTime;
    private String endTime;
    private String content;
    private String from;

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
}
