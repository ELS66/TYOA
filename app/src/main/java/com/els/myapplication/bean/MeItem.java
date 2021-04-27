package com.els.myapplication.bean;

public class MeItem {

    private int id;
    private String name;
    private int drawable_id;

    public MeItem(int id,String name, int drawable_id) {
        this.id = id;
        this.name = name;
        this.drawable_id = drawable_id;
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

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
    }
}
