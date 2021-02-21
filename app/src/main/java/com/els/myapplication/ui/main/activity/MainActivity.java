package com.els.myapplication.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.els.myapplication.Constant;
import com.els.myapplication.R;

import com.els.myapplication.bean.User;
import com.els.myapplication.push.MyXGRecevier;
import com.els.myapplication.utils.ShpUtil;
import com.els.myapplication.utils.WebUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;
    public static Activity activity;
    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ShpUtil shpUtil = new ShpUtil(MainActivity.this,"login");
        Gson gson = new Gson();
        System.out.println(shpUtil.load("user"));
        user = gson.fromJson(shpUtil.load("user"),User.class);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_function, R.id.navigation_user)
                .build();
        /*Intent intent = new Intent();
           intent.setClass(MainActivity.this, LoginActivity.class);
           startActivity(intent);*/

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        /*Uri uri = getIntent().getData();
        if (uri != null) {
            String p1 = uri.getQueryParameter("param1");
            if (p1.equals("leave")){
                navController.navigate(R.id.action_navigation_home_to_leaveFragment);
            }
        }*/



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult != null){
            if (intentResult.getContents() == null){
                Toast.makeText(this,"扫码失败！",Toast.LENGTH_SHORT).show();
            } else {
                String result = intentResult.getContents();
                Bundle bundle = new Bundle();
                bundle.putString("uid",result);
                NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_equipmentShowFragment,bundle);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyXGRecevier myXGRecevier = new MyXGRecevier();
        XGPushClickedResult xgPushClickedResult = new XGPushClickedResult();
        myXGRecevier.onNotificationClickedResult(this,xgPushClickedResult);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShpUtil shpUtil = new ShpUtil(this,"login");
        if (shpUtil.load("login").equals("false")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String strUrl = Constant.WEB_ADDRESS + "/exit";
                    Map<String,String> map = new HashMap<>();
                    map.put("id", String.valueOf(user.getId()));
                    WebUtil.loginsend(strUrl,map);
                }
            }).start();
        }
    }
}