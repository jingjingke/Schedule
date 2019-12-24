package com.jingjingke.schedule;

public class Progress {
    private String startTime;
    private String endingTime;
    private String remark;

    public Progress(String startTime, String endingTime, String remark) {
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public String getRemark() {
        return remark;
    }
}