package com.els.myapplication.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.els.myapplication.R;
import com.els.myapplication.ui.login.LoginActivity;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.ShpUtil;

public class EmployeeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_employee,container,false);
        Button button = root.findViewById(R.id.button_exitlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShpUtil shpUtil = new ShpUtil(requireContext(),"login");
                shpUtil.clear();
                Intent intent = new Intent();
                intent.setClass(MainActivity.activity, LoginActivity.class);
                startActivity(intent);
                MainActivity.activity.finish();
            }
        });
        
        return root;
    }
}
