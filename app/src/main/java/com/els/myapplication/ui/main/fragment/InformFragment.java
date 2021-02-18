package com.els.myapplication.ui.main.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;


public class InformFragment extends Fragment {

    EditText editText;
    Button button_all,button_portion;
    List<String> listname = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_infrom, container, false);
        init(root);
        return root;
    }

    private void init(View view){
        editText = view.findViewById(R.id.edittext_inform);
        button_all = view.findViewById(R.id.button_inform_all);
        button_portion = view.findViewById(R.id.button_inform_portion);
        button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) requireContext().getApplicationContext().getSystemService(requireContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> map = new HashMap<>();
                        map.put("is","0");
                        map.put("id", String.valueOf(MainActivity.user.getId()));
                        map.put("name",MainActivity.user.getUsername());
                        map.put("content",editText.getText().toString());
                        map.put("token","all");
                        String strUrl = Constant.WEB_ADDRESS + "/inform";
                        String res = WebUtil.loginsend(strUrl,map);
                        Message message = new Message();
                        if (res.equals("true")){
                            message.what = 0;
                        } else {
                            message.what = 1;
                        }
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        button_portion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> map = new HashMap<>();
                        map.put("is","1");
                        String strUrl = Constant.WEB_ADDRESS+"/inform";
                        Message message = new Message();
                        String res = WebUtil.loginsend(strUrl,map);
                        message.obj = res;
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0: {
                    Toast.makeText(requireActivity(),"发送成功！",Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).popBackStack();
                    break;
                }
                case 1: {
                    Toast.makeText(requireActivity(),"发送失败！",Toast.LENGTH_SHORT).show();
                    break;
                }
                case 2: {
                    final Gson gson = new Gson();
                    final String[] strings = gson.fromJson(msg.obj.toString(),String[].class);
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                    dialog.setTitle("选择人员");
                    listname.clear();
                    dialog.setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if (b){
                                listname.add(strings[i]);
                            }
                        }
                    });
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (listname.size() == 0) {
                                Toast.makeText(requireContext(),"请至少选择一位人员",Toast.LENGTH_SHORT).show();
                            } else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String res = gson.toJson(listname);
                                        Map<String,String> mapres = new HashMap<>();
                                        mapres.put("is","2");
                                        mapres.put("id", String.valueOf(MainActivity.user.getId()));
                                        mapres.put("list",res);
                                        mapres.put("content",editText.getText().toString().trim());
                                        mapres.put("name",MainActivity.user.getUsername());
                                        String strUrl = Constant.WEB_ADDRESS + "inform";
                                        String s =WebUtil.loginsend(strUrl,mapres);
                                        Message message = new Message();
                                        if (s.equals("true")){
                                            message.what = 0;
                                        } else {
                                            message.what = 1;
                                        }
                                        handler.sendMessage(message);
                                    }
                                }).start();

                            }
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();
                    break;
                }
                default:{
                    break;
                }
            }

        }
    };
}