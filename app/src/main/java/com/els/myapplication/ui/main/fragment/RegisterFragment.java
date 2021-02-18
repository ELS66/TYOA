package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.utils.MyUtil;
import com.els.myapplication.utils.WebUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    EditText editTextname,editTextpass,editTextpassagn;
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register,container,false);
        init(root);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editTextname.getText().toString();
                final String pass = editTextpass.getText().toString();
                String passag = editTextpassagn.getText().toString();
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
                                                }
                                            }).start();
                                        }else {
                                            Toast.makeText(requireContext(),"两次输入的密码不相同",Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(requireContext(),"密码请用字母和数字组合",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(requireContext(),"密码中不能含有中文字符",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(requireContext(),"密码长度请设置在8到16位",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(requireContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    private void init(View view) {
        editTextname = view.findViewById(R.id.editTextRegName);
        editTextpass = view.findViewById(R.id.editTextRegPass);
        editTextpassagn = view.findViewById(R.id.editTextRegPassAg);
        button = view.findViewById(R.id.button_reg);
    }


}
