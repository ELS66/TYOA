package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.els.myapplication.R;
import com.els.myapplication.adapter.MyAdapter;
import com.els.myapplication.bean.MyItem;
import com.els.myapplication.ui.main.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class EquipmentHomeFragment extends Fragment {

    List<MyItem> itemList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemList.add(new MyItem("添加设备",R.drawable.item_set,R.drawable.item_to));
        itemList.add(new MyItem("管理设备",R.drawable.item_set,R.drawable.item_to));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View root =inflater.inflate(R.layout.fragment_equipment_home, container, false);
        init(root);
        MainActivity.tv_title.setText("设备");
        return root;
    }

    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_equipment_home);
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        myAdapter.submitList(itemList);
    }
}