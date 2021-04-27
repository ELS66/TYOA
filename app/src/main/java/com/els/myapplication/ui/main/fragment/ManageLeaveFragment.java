package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.els.myapplication.R;
import com.els.myapplication.ui.main.activity.MainActivity;


public class ManageLeaveFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_manage_leave, container, false);
        init(view);
        MainActivity.tv_title.setText("请假管理");
        return view;
    }

    private void init(View view) {

    }

}