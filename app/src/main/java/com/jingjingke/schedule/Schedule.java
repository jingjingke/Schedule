package com.jingjingke.schedule;

public class Schedule {
    private String name;
    private String cost;
    private String status;

    public Schedule(String name, String cost) {
        this.name = name;
        this.cost = cost;
    }

    public Schedule(String name, String cost, String status) {
        this.name = name;
        this.cost = cost;
        this.status = status;
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
