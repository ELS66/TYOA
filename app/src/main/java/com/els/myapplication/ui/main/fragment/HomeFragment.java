package com.els.myapplication.ui.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.ui.qccode.QrCodeActivity;
import com.els.myapplication.utils.QcCodeUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*XGPushConfig.enableDebug(requireContext(),true);
                XGPushManager.registerPush(requireContext(), new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object o, int i) {
                        Log.d("TPushELS","token"+o.toString());
                    }

                    @Override
                    public void onFail(Object o, int i, String s) {
                        Log.d("TPushELS",i+"   "+s);
                    }
                });*/
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.item_scan);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new IntentIntegrator(requireActivity())
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        .setCameraId(0)
                        .setPrompt("11111")
                        .setBeepEnabled(true)
                        .setCaptureActivity(QrCodeActivity.class)
                        .initiateScan();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }


}