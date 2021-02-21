package com.els.myapplication.bean;

public class Equipment {
    private int uid;
    private String name;
    private String model;
    private String project;

    public Equipment(int uid, String name, String model, String project) {
        this.uid = uid;
        this.name = name;
        this.model = model;
        this.project = project;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
