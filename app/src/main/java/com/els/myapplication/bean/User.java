package com.els.myapplication.bean;



public class User {
    private int id;
    private String username;
    private String password;
    private int root;
    private int day;
    private String token;

    public User(int id, String username, String password, int root, int day, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.root = root;
        this.day = day;
        this.token = token;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public int getLeave() {
        return day;
    }

    public void setLeave(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ",username=" + username+",password="+password+"root=" + root +"leave=" + day +"}";
    }
}
