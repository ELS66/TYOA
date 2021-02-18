package com.els.myapplication.ui.main.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.adapter.MessAdapter;
import com.els.myapplication.bean.MessItem;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.ShpUtil;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    MessAdapter messAdapter;
    RecyclerView recyclerView;
    List<MessItem> mData = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message, container, false);
        init(root);
        messAdapter = new MessAdapter(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(messAdapter);
        handlerDownPullUpdate();

        return root;
    }

    private void handlerDownPullUpdate() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int num = mData.size();
                        Map<String,String> map = new HashMap<>();
                        map.put("num",String.valueOf(num));
                        map.put("id", String.valueOf(MainActivity.user.getId()));
                        String strUrl = Constant.WEB_ADDRESS + "/mess";
                        String n = WebUtil.loginsend(strUrl,map);
                        Log.d("ELS",n);
                        Message message = new Message();
                        if (!n.equals("false")){
                            List<MessItem> list = new ArrayList<>();
                            Gson gson = new Gson();
                            list = gson.fromJson(n,new TypeToken<List<MessItem>>(){}.getType());
                            System.out.println(list.get(0).getDate());
                            for (int i=0; i<list.size();i++) {
                                mData.add(0,list.get(i));
                                System.out.println(list.get(i).getDate());
                            }
                            message.what = 1;
                        } else {
                            message.what = 0;
                        }
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    private void init(View view){
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView = view.findViewById(R.id.recyclerView_mess);
        Gson gson = new Gson();
        ShpUtil shpUtil = new ShpUtil(requireContext(),"mess");
        String strjson = shpUtil.load("mess");
        if (!strjson.equals("null")){
            mData = gson.fromJson(strjson,new TypeToken<List<MessItem>>(){}.getType());
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message message){
            if (message.what == 0) {
                Toast.makeText(requireContext(),"cuowu",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
            if (message.what == 1){
                messAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                ShpUtil shpUtil = new ShpUtil(requireContext(),"mess");
                String str = gson.toJson(mData);
                shpUtil.save("mess",str);
            }
        }
    };
}