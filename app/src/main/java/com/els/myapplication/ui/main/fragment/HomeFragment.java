package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.els.myapplication.R;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XGPushConfig.enableDebug(requireContext(),true);
                XGPushManager.registerPush(requireContext(), new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object o, int i) {
                        Log.d("TPushELS","token"+o.toString());
                    }

                    @Override
                    public void onFail(Object o, int i, String s) {
                        Log.d("TPushELS",i+"   "+s);
                    }
                });
            }
        });
        return root;
    }
}