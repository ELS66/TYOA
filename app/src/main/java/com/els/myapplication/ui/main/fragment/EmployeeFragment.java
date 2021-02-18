package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.els.myapplication.R;
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

            }
        });
        
        return root;
    }
}
