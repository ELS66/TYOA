package com.els.myapplication.push;

import android.content.Context;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.ShpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.android.tpush.NotificationAction;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.util.ArrayList;
import java.util.List;

public class MyXGRecevier extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onSetAccountResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteAccountResult(Context context, int i, String s) {

    }

    @Override
    public void onSetAttributeResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteAttributeResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {

    }

    @Override
    public void onNotificationClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotificationShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        /*Log.d(Constant.TAG, String.valueOf(xgPushShowedResult.getMsgId()));
        Log.d(Constant.TAG,xgPushShowedResult.getTitle());
        Log.d(Constant.TAG,xgPushShowedResult.getContent());
        Log.d(Constant.TAG,xgPushShowedResult.getCustomContent());*/
        /*if (xgPushShowedResult.getTitle().equals("通知")) {
            List<MessItem> list = new ArrayList<>();
            Gson gson = new Gson();
            ShpUtil shpUtil = new ShpUtil(context,"mess");
            String strJson = shpUtil.load("mess");
            if (!strJson.equals("null")) {
                list = gson.fromJson(strJson,new TypeToken<List<MessItem>>(){}.getType());
            }
            list.add(0,)
        }*/
    }
}
