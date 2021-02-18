package com.els.myapplication.bean;

import java.sql.Timestamp;

public class MessItem {
    private int id;
    private String title;
    private String content;
    private String date;
    private boolean islook;
    private String feedback;
    private int handler;
    private int requester;

    public MessItem(int id, String title, String content, String date,boolean islook,String feedback,int handler,int requester) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.islook = islook;
        this.feedback = feedback;
        this.handler = handler;
        this.requester = requester;
    }

    public MessItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isIslook() {
        return islook;
    }

    public void setIslook(boolean islook) {
        this.islook = islook;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getHandler() {
        return handler;
    }

    public void setHandler(int handler) {
        this.handler = handler;
    }

    public int getRequester() {
        return requester;
    }

    public void setRequester(int requester) {
        this.requester = requester;
    }
}
