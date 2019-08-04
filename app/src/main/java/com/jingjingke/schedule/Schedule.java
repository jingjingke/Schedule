package com.jingjingke.schedule;

import android.text.TextUtils;

public class Schedule {
    private String id;
    private String name;
    private String cost;
    private String status;

    public Schedule(String id, String name, String cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }


    public Schedule(String id, String name, String cost, String status) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

}
