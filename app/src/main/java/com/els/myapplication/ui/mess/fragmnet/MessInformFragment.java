package com.els.myapplication.ui.mess.fragmnet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessInformFragment extends Fragment {

    TextView textView_text,textView_name,textView_date;
    MessItem messItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mess_inform,null);
        init(root);
        return root;
    }

    private void init(View view){
        textView_name = view.findViewById(R.id.textView_mess_inform_name);
        textView_text = view.findViewById(R.id.textView_mess_inform_text);
        textView_date = view.findViewById(R.id.textView_mess_inform_date);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String mess = bundle.getString("messItem");
            String name = bundle.getString("name");
            Gson gson = new Gson();
            messItem = gson.fromJson(mess,MessItem.class);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = simpleDateFormat.parse(messItem.getDate());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日");
                String date2 = simpleDateFormat2.format(date);
                textView_date.setText(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (name.equals("0")) {
                textView_name.setText("系统发送");
            } else {
                textView_name.setText("发布人： " + name);
            }
            textView_text.setText(messItem.getContent());
        }
    }

}
