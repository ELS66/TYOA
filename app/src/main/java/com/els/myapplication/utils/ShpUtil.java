package com.els.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class ShpUtil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ShpUtil(Context context, String shpname) {
        sharedPreferences =context.getSharedPreferences(shpname,Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
    }

    public void save(String tag,String string){
        editor.remove(tag);
        editor.putString(tag,string);
        editor.commit();
    }

    public String load(String tag){
        return sharedPreferences.getString(tag,"null");
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}
