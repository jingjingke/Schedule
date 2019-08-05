package com.jingjingke.schedule;

public class Schedule {
    private int id;
    private String name;
    private String content;
    private String remark;
    private String status;
    private int sid;
    private String create;
    private String cost;

    public Schedule() {
    }

    public Schedule(int id, String name, String content, String remark, String status, int sid, String create, String cost) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.remark = remark;
        this.status = status;
        this.sid = sid;
        this.create = create;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getRemark() {
        return remark;
    }

    public String getStatus() {
        return status;
    }

    public int getSid() {
        return sid;
    }

    public String getCreate() {
        return create;
    }

    public String getCost() {
        return cost;
    }
}
