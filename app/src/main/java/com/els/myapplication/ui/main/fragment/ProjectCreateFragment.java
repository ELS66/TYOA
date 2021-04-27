package com.els.myapplication.ui.main.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProjectCreateFragment extends Fragment {

    EditText editText_name;
    Button button;
    MultiSpinnerSearch spinner_employee,spinner_manage;
    List<String> employeelist = new ArrayList<>();
    List<String> managementlist = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strUrl = Constant.WEB_ADDRESS + "/project";
                Map<String,String> map = new HashMap<>();
                map.put("is","0");
                String res = WebUtil.loginsend(strUrl,map);
                Message message = new Message();
                message.what = 0;
                message.obj = res;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_project_create, container, false);
        init(root);
        MainActivity.tv_title.setText("添加项目");
        return root;
    }

    private void init(View view) {
        editText_name = view.findViewById(R.id.editText_project_create_name);
        spinner_manage = view.findViewById(R.id.spinner_project_create_manage);
        spinner_employee = view.findViewById(R.id.spinner_project_create_employee);
        button = view.findViewById(R.id.button_project_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_name.getText() == null) {
                    Toast.makeText(requireContext(),"请输入项目名称",Toast.LENGTH_SHORT).show();
                } else {
                    if (spinner_manage.getSelectedItems().size() == 0 ) {
                        Toast.makeText(requireContext(),"请选择管理人员",Toast.LENGTH_SHORT).show();
                    } else {
                        if (spinner_employee.getSelectedItems().size() ==0) {
                            Toast.makeText(requireContext(),"请选择工作人员",Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<KeyPairBoolData> list1 = spinner_employee.getSelectedItems();
                                    List<KeyPairBoolData> list2 = spinner_manage.getSelectedItems();
                                    for (int i=0;i<list1.size();i++) {
                                        if (list1.get(i).isSelected()) {
                                            employeelist.add(list1.get(i).getName());
                                        }
                                    }
                                    for (int i =0;i<list2.size();i++) {
                                        if (list2.get(i).isSelected()) {
                                            managementlist.add(list2.get(i).getName());
                                        }
                                    }
                                    String strUrl = Constant.WEB_ADDRESS+"/project";
                                    Gson gson = new Gson();
                                    String employee = gson.toJson(employeelist);
                                    String management = gson.toJson(managementlist);
                                    Map<String,String> map = new HashMap<>();
                                    map.put("is","1");
                                    map.put("employee",employee);
                                    map.put("management",management);
                                    map.put("name",editText_name.getText().toString());
                                    String res = WebUtil.loginsend(strUrl,map);
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = res;
                                    handler.sendMessage(message);
                                }
                            }).start();
                        }
                    }

                }
            }
        });
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0: {
                    String res = msg.obj.toString();
                    System.out.println(res);
                    Gson gson = new Gson();
                    List<List<String>> lists = gson.fromJson(res,new TypeToken<List<List<String>>>(){}.getType());
                    List<String> listemployee = lists.get(0);
                    List<String> listmanage = lists.get(1);
                    List<KeyPairBoolData> list0 = new ArrayList<>();
                    for (int i = 0; i< listemployee.size();i++) {
                        KeyPairBoolData h = new KeyPairBoolData();
                        h.setId(i+1);
                        h.setName(listemployee.get(i));
                        h.setSelected(false);
                        list0.add(h);
                    }
                    spinner_employee.setSearchEnabled(true);
                    spinner_employee.setSearchHint("请输入人员姓名");
                    spinner_employee.setEmptyTitle("没有找到，请重新输入");
                    spinner_employee.setClearText("清除选择");
                    spinner_employee.setItems(list0, new MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            /*for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).isSelected()) {
                                    employeelist.add(items.get(i).getName());
                                }
                            }*/
                        }
                    });
                    List<KeyPairBoolData> list1 = new ArrayList<>();
                    for (int i=0;i<listmanage.size();i++) {
                        KeyPairBoolData h = new KeyPairBoolData();
                        h.setId(i+1);
                        h.setName(listmanage.get(i));
                        h.setSelected(false);
                        list1.add(h);
                    }
                    spinner_manage.setSearchEnabled(true);
                    spinner_manage.setSearchHint("请输入人员姓名");
                    spinner_manage.setEmptyTitle("没有找到，请重新输入");
                    spinner_manage.setClearText("清除选择");
                    spinner_manage.setItems(list1, new MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                            /*for (int i=0;i<selectedItems.size();i++) {
                                if (selectedItems.get(i).isSelected()) {
                                    managementlist.add(selectedItems.get(i).getName());
                                }
                            }*/
                        }
                    });
                    break;
                }
                case 1: {
                    String uid = msg.obj.toString();
                    AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                            .setTitle("创建成功")
                            .setMessage("工程编号为：" + uid)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Navigation.findNavController(getView()).popBackStack();
                                }
                            })
                            .create();
                    alertDialog.show();
                    break;
                }
            }
        }
    };
}