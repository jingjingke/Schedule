package com.jingjingke.schedule;

public class Schedule {
    private String id;
    private String name;
    private String cost;
    private int status;
    private String status_name;
    private String content;
    private String remark;
    private String create_time;

    public Schedule(){

    }

    public Schedule(String id, String name, String cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }


    public Schedule(String id, String name, String cost, String status_name) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.status_name = status_name;
    }

    public Schedule(String id, String name, String content, String remark, String cost, int status,String create_time) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.status = status;
        this.content = content;
        this.remark = remark;
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getStatus_name() {
        return status_name;
    }
}
