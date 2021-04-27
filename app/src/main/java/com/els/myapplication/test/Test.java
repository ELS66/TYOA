package com.els.myapplication.test;

import com.els.myapplication.R;
import com.els.myapplication.bean.MeItem;
import com.els.myapplication.bean.MessItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        MeItem meItem = new MeItem(0,"请假", R.drawable.ic_leave);
        MeItem meItem1 = new MeItem(1,"请假管理",R.drawable.ic_leave_manage);
        List<MeItem> list = new ArrayList<>();
        list.add(meItem);
        list.add(meItem1);
        Gson gson = new Gson();
        String res = gson.toJson(list);
        System.out.println(res);
    }
}
