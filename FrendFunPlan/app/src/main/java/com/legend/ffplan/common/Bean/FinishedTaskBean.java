package com.legend.ffplan.common.Bean;

/**
 * @author Legend
 * @data by on 2018/1/15.
 * @description
 */

public class FinishedTaskBean {

    private String time;
    private String date;
    private String content;

    public FinishedTaskBean(String time,String date,String content) {
        this.time = time;
        this.date = date;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
