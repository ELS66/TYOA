package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.els.myapplication.R;
import com.els.myapplication.adapter.MyAdapter;
import com.els.myapplication.bean.MyItem;
import com.els.myapplication.bean.User;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.MyOneLineView;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    TextView textname,textid,textregular;
    List<MyItem> itemUserList = new ArrayList<>();
    List<MyItem> itemBossList = new ArrayList<>();
    RecyclerView recyclerView;
    View root;
    User user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemUserList.add(new MyItem("请假",R.drawable.item_mess,R.drawable.item_to));
        itemUserList.add(new MyItem("设置",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("设置",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("请假管理",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("通知",R.drawable.item_set,R.drawable.item_set));
        itemBossList.add(new MyItem("项目",R.drawable.item_set,R.drawable.item_to));
        itemBossList.add(new MyItem("设备",R.drawable.item_set,R.drawable.item_to));
        user = MainActivity.user;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (MainActivity.user.getRoot() == 0) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
            initUser(root);
        } else {
            root = inflater.inflate(R.layout.fragment_boss,container,false);
            initBoss(root);
        }


        /*button_ToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShpUtil shpUtil = new ShpUtil(requireContext(),"login");
                shpUtil.clear();
                Intent intent = new Intent();
                intent.setClass(requireContext(), LoginActivity.class);
                startActivity(intent);
                MainActivity.activity.finish();
            }
        });*/

        return root;
    }

    private  void initUser(View view){
        textname = view.findViewById(R.id.textView_user_name);
        textid = view.findViewById(R.id.textView_user_id);
        textregular = view.findViewById(R.id.textView_user_regular);
        textname.setText(user.getUsername());
        textid.setText(String.valueOf(user.getId()));
        if (user.getId() == 1) {
            textregular.setText("正式员工");
        } else {
            textregular.setText("临时员工");
        }
        recyclerView = view.findViewById(R.id.recyclerView_user);
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        myAdapter.submitList(itemUserList);
    }

    private void initBoss(View view){
        textname = view.findViewById(R.id.textView_boss_name);
        textid = view.findViewById(R.id.textView_boss_id);
        textregular = view.findViewById(R.id.textView_boss_regular);
        if (user.getId() == 1) {
            textregular.setText("正式员工");
        } else {
            textregular.setText("临时员工");
        }
        recyclerView = view.findViewById(R.id.recyclerView_boss);
        textname.setText(user.getUsername());
        textid.setText(String.valueOf(user.getId()));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        myAdapter.submitList(itemBossList);
    }




}