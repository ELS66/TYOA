package com.els.myapplication.ui.mess.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.els.myapplication.ui.mess.fragmnet.MessInformFragment;
import com.els.myapplication.ui.mess.fragmnet.MessLeaveFragment;
import com.els.myapplication.utils.WebUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessActivity extends AppCompatActivity {

    String p1,p2,p3;
    List<MessItem> mData = new ArrayList<>();
    List<MessItem> newData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        Uri uri = getIntent().getData();

        int num = 0;
        if (uri != null){
            String url = uri.toString();
            p1 = uri.getQueryParameter("param1");
            p2 = uri.getQueryParameter("param2");
            p3 = uri.getQueryParameter("param3");

            if (p1 != null) {
                switch (p1) {
                    case "请假": {
                        if (p2 != null) {
                            if (!p2.equals("0")){
                                num = Integer.parseInt(p2);
                            }
                            if (num != 0) {
                                send(num,1);
                            }
                        }
                        break;
                    }
                    case "通知": {
                        if (p2 != null){
                            num = Integer.parseInt(p2);
                        }
                        if (num != 0){
                            send(num,2);
                        }
                    }
                }
            }
        }

    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message message){
            switch (message.what) {
                case 0: {
                    System.out.println("res == 0");
                    break;
                }
                case 1: {
                    Bundle bundle = new Bundle();
                    bundle.putString("messItem",message.obj.toString());
                    MessLeaveFragment messLeaveFragment = new MessLeaveFragment();
                    messLeaveFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mess_framelayout, messLeaveFragment);
                    fragmentTransaction.commit();
                    break;
                }
                case 2: {
                    Bundle bundle = new Bundle();
                    bundle.putString("messItem",message.obj.toString());
                    bundle.putString("name",p3);
                    MessInformFragment messInformFragment = new MessInformFragment();
                    messInformFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mess_framelayout,messInformFragment);
                    fragmentTransaction.commit();
                }
            }
        }
    };

    private void send(int num, final int what) {
        final int finalNum = num;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map = new HashMap<>();
                map.put("num",String.valueOf(finalNum));
                String strUrl = Constant.WEB_ADDRESS + "/messlayout";
                String res = WebUtil.loginsend(strUrl,map);
                Message message = new Message();
                if (res.equals("false")) {
                    message.what = 0;
                } else {
                    message.what = what;
                    message.obj = res;
                }
                handler.sendMessage(message);
            }
        }).start();
    }
}