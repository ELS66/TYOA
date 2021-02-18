package com.els.myapplication.bean;

import android.graphics.drawable.Drawable;

public class MyItem {

    private String text;
    private int drawable_left,drawable_right;

    public MyItem(String text, int drawable_left, int drawable_right) {
        this.text = text;
        this.drawable_left = drawable_left;
        this.drawable_right = drawable_right;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDrawable_left() {
        return drawable_left;
    }

    public void setDrawable_left(int drawable_left) {
        this.drawable_left = drawable_left;
    }

    public int getDrawable_right() {
        return drawable_right;
    }

    public void setDrawable_right(int drawable_right) {
        this.drawable_right = drawable_right;
    }
}
