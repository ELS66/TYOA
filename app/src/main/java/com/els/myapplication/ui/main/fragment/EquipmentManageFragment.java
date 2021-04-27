package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.adapter.EquipmentAdapter;
import com.els.myapplication.bean.Equipment;
import com.els.myapplication.bean.Project;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EquipmentManageFragment extends Fragment {

    RecyclerView recyclerView;
    List<Equipment> equipmentList;
    EquipmentAdapter equipmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_equipment_manage, container, false);
        init(root);
        MainActivity.tv_title.setText("设备管理");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strUrl = Constant.WEB_ADDRESS + "equipment";
                Map<String,String> map = new HashMap<>();
                map.put("is","1");
                String res = WebUtil.loginsend(strUrl,map);
                Message message = new Message();
                message.what = 0;
                message.obj = res;
                Log.d(Constant.TAG,res);
                handler.sendMessage(message);
            }
        }).start();
        return root;
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                Gson gson = new Gson();
                equipmentList = gson.fromJson(msg.obj.toString(),new TypeToken<List<Equipment>>(){}.getType());
                equipmentAdapter = new EquipmentAdapter(equipmentList);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(equipmentAdapter);
            }
        }
    };

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_equipment_manage);
    }
}