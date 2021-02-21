package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.Project;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProjectShowFragment extends Fragment {

    TextView textView_name,textView_id,textView_manage,textView_employee;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_show, container, false);
        init(root);
        return root;
    }

    private void init(View view) {
        textView_name = view.findViewById(R.id.textView_project_show_name);
        textView_id = view.findViewById(R.id.textView_project_show_id);
        textView_manage = view.findViewById(R.id.textView_project_show_manage_text);
        textView_employee = view.findViewById(R.id.textView_project_show_employee_text);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String uid = bundle.getString("uid");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String strUrl = Constant.WEB_ADDRESS + "project";
                    Map<String,String> map = new HashMap<>();
                    map.put("is","3");
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

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Gson gson = new Gson();
                Project project = gson.fromJson(msg.obj.toString(),Project.class);
                textView_name.setText(project.getName());
                textView_id.setText(String.valueOf(project.getUid()));
                textView_manage.setText(project.getManagement());
                textView_employee.setText(project.getEmployee());
            }
        }
    };
}