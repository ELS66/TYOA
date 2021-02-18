package com.els.myapplication.ui.mess.fragmnet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.ui.mess.activity.MessActivity;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MessLeaveFragment extends Fragment {

    TextView textView_title,textView_content;
    Button button_yes,button_no;
    MessItem messItem;
    EditText editText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mess_leave,null);
        init(root);
        return root;
    }

    private void init(View view) {
        textView_title = view.findViewById(R.id.textView_mess_leave_title);
        textView_content = view.findViewById(R.id.textView_mess_leave_content);
        button_yes = view.findViewById(R.id.button_mess_leave_yes);
        button_no = view.findViewById(R.id.button_mess_leave_no);
        Bundle bundle = this.getArguments();
        String mess;
        if (bundle != null){
            mess = bundle.getString("messItem");
            Gson gson = new Gson();
             messItem = gson.fromJson(mess,MessItem.class);
            textView_title.setText(messItem.getTitle());
            textView_content.setText(messItem.getContent());
        }
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> map = new HashMap<>();
                        map.put("feedback","true");
                        map.put("id",String.valueOf(messItem.getId()));
                        map.put("handler",String.valueOf(MainActivity.user.getId()));
                        map.put("requester",String.valueOf(messItem.getRequester()));
                        String strUrl = Constant.WEB_ADDRESS + "/feedback";
                        WebUtil.loginsend(strUrl,map);
                        Intent intent = new Intent();
                        intent.setClass(requireActivity(),MainActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }).start();
            }
        });
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {

        final View dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_feedback,null);
        final AlertDialog alertDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("批示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editText.getText() != null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Map<String,String> map = new HashMap<>();
                                    map.put("feedback","false");
                                    map.put("id",String.valueOf(messItem.getId()));
                                    map.put("handler",String.valueOf(MainActivity.user.getId()));
                                    map.put("requester",String.valueOf(messItem.getRequester()));
                                    map.put("text",editText.getText().toString().trim());
                                    String strUrl = Constant.WEB_ADDRESS + "/feedback";
                                    WebUtil.loginsend(strUrl,map);
                                }
                            }).start();
                            Intent intent = new Intent();
                            intent.setClass(requireActivity(),MainActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(requireActivity(),"请输入内容",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setView(dialogView)
                .create();
        editText = dialogView.findViewById(R.id.dialog_feed_edit);
        alertDialog.show();
    }
}
