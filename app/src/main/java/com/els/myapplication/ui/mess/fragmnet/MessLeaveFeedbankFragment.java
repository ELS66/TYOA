package com.els.myapplication.ui.mess.fragmnet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.google.gson.Gson;


public class MessLeaveFeedbankFragment extends Fragment {

    TextView textView_title,textView_content,textView_handler,textView_date;
    MessItem messItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mess_leave_feedbank, container, false);
        init(root);
        return root;
    }


    private void init(View view) {
        textView_title = view.findViewById(R.id.textView_mess_leave_feedback_title);
        textView_content = view.findViewById(R.id.textView_mess_leave_feedback_content);
        textView_handler = view.findViewById(R.id.textView_mess_leave_feedback_handler);
        textView_date = view.findViewById(R.id.textView_mess_leave_feedback_date);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String is = bundle.getString("is");
            String mess = bundle.getString("messItem");
            Gson gson = new Gson();
            messItem = gson.fromJson(mess,MessItem.class);
            textView_title.setText(messItem.getTitle());
            textView_handler.setText("处理人： " + messItem.getParam());
            textView_date.setText(messItem.getDate());
            if (is.equals("yes")) {
                textView_content.setVisibility(View.INVISIBLE);
            } else {
                textView_content.setText(messItem.getContent());
            }
        }
    }
}