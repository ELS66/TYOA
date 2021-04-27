package com.els.myapplication.bean;

public class Financial {
    private int id;
    private String name;
    private String project;
    private String date;
    private boolean ispass;
    private long money;
    private String requester;
    private String title;

    public Financial(int id, String name, String project, String date, boolean ispass, long money, String requester,String title) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.date = date;
        this.ispass = ispass;
        this.money = money;
        this.requester = requester;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isIspass() {
        return ispass;
    }

    public void setIspass(boolean ispass) {
        this.ispass = ispass;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
