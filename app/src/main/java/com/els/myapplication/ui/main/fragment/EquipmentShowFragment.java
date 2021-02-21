package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.Equipment;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EquipmentShowFragment extends Fragment {

    TextView textView_name,textView_id,textView_model;
    SingleSpinnerSearch singleSpinnerSearch;
    Button button;
    List<String> projectList;
    String old;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_equipment_show, container, false);
        init(root);
        return root;
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                Gson gson = new Gson();
                String[] strings = gson.fromJson(msg.obj.toString(),String[].class);
                Equipment equipment = gson.fromJson(strings[0],Equipment.class);
                textView_name.setText(equipment.getName());
                textView_id.setText(String.valueOf(equipment.getUid()));
                textView_model.setText(equipment.getModel());
                projectList = gson.fromJson(strings[1],new TypeToken<List<String>>(){}.getType());
                List<KeyPairBoolData> list = new ArrayList<>();
                if (equipment.getProject().equals("null")){
                    KeyPairBoolData k = new KeyPairBoolData();
                    k.setId(1);
                    k.setName(projectList.get(0));
                    k.setSelected(true);
                    list.add(k);
                    old = "null";
                    for (int i =1;i<projectList.size();i++) {
                        KeyPairBoolData h = new KeyPairBoolData();
                        h.setId(i+1);
                        h.setName(projectList.get(i));
                        h.setSelected(false);
                        list.add(h);
                    }
                } else {
                    old = equipment.getProject();
                    for (int i=0;i<projectList.size();i++) {
                        KeyPairBoolData h = new KeyPairBoolData();
                        h.setId(i+1);
                        h.setName(projectList.get(i));
                        if (projectList.get(i).equals(old)) {
                            h.setSelected(true);
                        } else {
                            h.setSelected(false);
                        }
                        list.add(h);
                    }
                }
                singleSpinnerSearch.setSearchHint("搜索项目");
                singleSpinnerSearch.setItems(list, new SingleSpinnerListener() {
                    @Override
                    public void onItemsSelected(KeyPairBoolData selectedItem) {
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String str = selectedItem.getName();
                                        String strUrl = Constant.WEB_ADDRESS + "equipment";
                                        Map<String,String> map = new HashMap<>();
                                        map.put("is","3");
                                        map.put("old",old);
                                        map.put("equipment",equipment.getName());
                                        map.put("uid",String.valueOf(equipment.getUid()));
                                        map.put("name",str);
                                        String res = WebUtil.loginsend(strUrl,map);
                                    }
                                }).start();
                            }
                        });

                    }

                    @Override
                    public void onClear() {

                    }
                });
            }
        }
    };

    private void init(View view){
        textView_name = view.findViewById(R.id.textView_equipment_show_name);
        textView_id = view.findViewById(R.id.textView_equipment_show_id);
        textView_model = view.findViewById(R.id.textView_equipment_show_model);
        singleSpinnerSearch = view.findViewById(R.id.singlespinnerseach_equipment_show);
        button = view.findViewById(R.id.button_equipment_show);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String uid = bundle.getString("uid");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String strUrl = Constant.WEB_ADDRESS + "equipment";
                    Map<String,String> map = new HashMap<>();
                    map.put("is","2");
                    map.put("uid",uid);
                    String res = WebUtil.loginsend(strUrl,map);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                }
            }).start();
        }

    }
}