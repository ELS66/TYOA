package com.els.myapplication.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.base.BaseActivity;
import com.els.myapplication.bean.User;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.ui.reg.RegActivity;
import com.els.myapplication.utils.MyUtil;
import com.els.myapplication.utils.ShpUtil;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.tencent.android.tpush.NotificationAction;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static Activity activity;
    EditText editTextName,editTextPass;
    CheckBox checkBox;
    TextView textView,button;
    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        ShpUtil shpUtil = new ShpUtil(this,"login");
        if (shpUtil.load("login").equals("true")){
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        init();
        if (!shpUtil.load("name").equals("null")){
            editTextName.setText(shpUtil.load("name"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextPass.getText())){
                    Toast.makeText(LoginActivity.this,R.string.toast_login,Toast.LENGTH_SHORT).show();
                } else {
                    loading();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String name = editTextName.getText().toString();
                            final String pass = MyUtil.md5(editTextPass.getText().toString());
                            final List<String> stringList = new ArrayList<>();

                            XGPushConfig.enableDebug(LoginActivity.this,true);
                            XGPushManager.registerPush(LoginActivity.this, new XGIOperateCallback() {
                                @Override
                                public void onSuccess(Object o, int i) {
                                    Log.d("TPushELS","token"+o.toString());
                                    Map<String,String> map = new HashMap<>();
                                    map.put("user",name);
                                    map.put("pass",pass);
                                    map.put("token",o.toString());
                                    String strUrl = Constant.WEB_ADDRESS + "/login";
                                    String n = WebUtil.loginsend(strUrl,map);
                                    Message message = new Message();
                                    if (n.equals("1")){
                                        message.what = 0;
                                    } else if (n.equals("false")){
                                        message.what = 2;
                                    } else if (n.equals("0")){
                                        message.what = 3;
                                    } else  {
                                        message.what = 1;
                                        message.obj = n;
                                    }
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void onFail(Object o, int i, String s) {
                                    Log.d("TPushELS",i+"  ELS "+s);
                                }
                            });

                        }
                    }).start();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
    }


    private void init(){
        editTextName = findViewById(R.id.editText_Login_Name);
        editTextPass = findViewById(R.id.editText_Login_Pass);
        checkBox = findViewById(R.id.checkBox_LoginA);
        button = findViewById(R.id.button_loginA);
        textView = findViewById(R.id.textView_login_reg);
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message message){
            switch (message.what) {
                case 0 : {
                    dismiss();
                    Toast.makeText(LoginActivity.this,"因网络问题登录失败，请稍候再试",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 1 : {
                    ShpUtil shpUtil = new ShpUtil(LoginActivity.this,"login");
                    if (checkBox.isChecked()){
                        shpUtil.save("login","true");
                    } else {
                        shpUtil.save("login","false");
                    }
                    shpUtil.save("name",editTextName.getText().toString());
                    shpUtil.save("user",message.obj.toString());
                    dismiss();
                /*NavController navController = Navigation.findNavController(LoginActivity.this,R.id.nav_host_fragment);
                navController.navigate(R.id.action_loginFragment_to_employeeFragment);*/
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this,"yes",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 2 : {
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 3 : {
                    Toast.makeText(LoginActivity.this,"网络连接错误！请检查网络或联系管理员",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult clickedResult = new XGPushClickedResult();
    }

    private void loading() {
        loading = new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(true)
                .hasStatusBarShadow(false)
                .hasShadowBg(false).asLoading();
        if (!loading.isShow()) {
            loading.show();
        }
    }

    private void dismiss() {
        try {
            if (loading.isShow()) {
                loading.dismiss();
            }
            loading = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}