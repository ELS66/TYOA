package com.els.myapplication.ui.reg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.ui.login.LoginActivity;
import com.els.myapplication.utils.MyUtil;
import com.els.myapplication.utils.WebUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity {

    EditText editTextName,editTextPass,editTextPassAg;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editTextName.getText().toString();
                final String pass = editTextPass.getText().toString();
                final String passag = editTextPassAg.getText().toString();
                try {
                    if (name.length() != 0){
                        if (pass.length() != 0){
                            if (pass.length() > 7 && pass.length() < 17){
                                if (!MyUtil.isChinese(pass)){
                                    if (MyUtil.passisok(pass)){
                                        if (pass.equals(passag)){
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    String passmd5 = MyUtil.md5(pass);
                                                    Map<String,String> map = new HashMap<>();
                                                    map.put("user",name);
                                                    map.put("pass",passmd5);
                                                    String strUrl = Constant.WEB_ADDRESS + "/register";
                                                    String res = WebUtil.loginsend(strUrl,map);
                                                    System.out.println(res);
                                                    Message message = new Message();
                                                    message.what = 0;
                                                    handler.sendMessage(message);
                                                }
                                            }).start();
                                        }else {
                                            Toast.makeText(RegActivity.this,"两次输入的密码不相同",Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(RegActivity.this,"密码请用字母和数字组合",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegActivity.this,"密码中不能含有中文字符",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(RegActivity.this,"密码长度请设置在8到16位",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init(){
        editTextName = findViewById(R.id.editText_Reg_Name);
        editTextPass = findViewById(R.id.editText_Reg_Pass);
        editTextPassAg = findViewById(R.id.editText_Reg_PassAg);
        button = findViewById(R.id.button_Reg);
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0){
                Intent intent = new Intent();
                intent.setClass(RegActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                LoginActivity.activity.finish();
            }
        }
    };


}