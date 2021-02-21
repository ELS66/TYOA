package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.adapter.ProjectAdapter;
import com.els.myapplication.bean.Project;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProjectManageFragment extends Fragment {

    RecyclerView recyclerView;
    List<Project> list;
    ProjectAdapter projectAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_project_manage, container, false);
        init(root);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map = new HashMap<>();
                map.put("is","2");
                String strUrl = Constant.WEB_ADDRESS + "project";
                String res = WebUtil.loginsend(strUrl,map);
                Message message = new Message();
                message.what = 0;
                message.obj = res;
                handler.sendMessage(message);
            }
        }).start();
        return root;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_project);
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Gson gson = new Gson();
                list = gson.fromJson(msg.obj.toString(),new TypeToken<List<Project>>(){}.getType());
                projectAdapter = new ProjectAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(projectAdapter);
            }
        }
    };
}