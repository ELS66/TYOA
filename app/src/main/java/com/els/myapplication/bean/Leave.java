package com.els.myapplication.bean;

public class Leave {
    private int id;
    private String name;
    private String begin;
    private String end;
    private boolean islook;
    private String handler;
    private String feedback;
    private String mess;

    public Leave(int id, String name, String begin, String end, boolean islook, String handler, String feedback,String mess) {
        this.id = id;
        this.name = name;
        this.begin = begin;
        this.end = end;
        this.islook = islook;
        this.handler = handler;
        this.feedback = feedback;
        this.mess = mess;
    }

    public Leave() {
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

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isIslook() {
        return islook;
    }

    public void setIslook(boolean islook) {
        this.islook = islook;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
