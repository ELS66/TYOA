package com.els.myapplication.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.MyUtil;
import com.els.myapplication.utils.WebUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LeaveFragment extends Fragment {

    TextView textView_begin,textView_end;
    Button button;
    Date date_begin,date_end;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leave,container,false);
        MainActivity.tv_title.setText("请假");
        init(root);
        textView_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerView pvTime = new TimePickerBuilder(requireContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        date_begin = date;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        textView_begin.setText(simpleDateFormat.format(date));
                    }
                }).build();
                pvTime.show();
            }
        });
        textView_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerView pvTime = new TimePickerBuilder(requireContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        date_end = date;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        textView_end.setText(simpleDateFormat.format(date));
                    }
                }).build();
                pvTime.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
                int year_begin = Integer.parseInt(sdf_year.format(date_begin));
                int year_end = Integer.parseInt(sdf_year.format(date_end));
                int day;
                if (year_begin == year_end){
                    SimpleDateFormat sdf_Day = new SimpleDateFormat("D");
                    int day_begin = Integer.parseInt(sdf_Day.format(date_begin));
                    int day_end = Integer.parseInt(sdf_Day.format(date_end));
                    day = day_end-day_begin+1;
                } else {
                    int day_one = 365;
                    if (MyUtil.bissextile(year_begin)){
                        day_one = 366;
                    }
                    SimpleDateFormat sdf_Day = new SimpleDateFormat("D");
                    day = day_one - Integer.parseInt(sdf_Day.format(date_begin)) +Integer.parseInt(sdf_Day.format(date_end))+1;
                }
                Toast.makeText(requireContext(),String.valueOf(day),Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String strUrl = Constant.WEB_ADDRESS + "/leave";
                        Map<String,String> map = new HashMap<>();
                        map.put("name",MainActivity.user.getUsername());
                        map.put("begin",textView_begin.getText().toString());
                        map.put("end",textView_end.getText().toString());
                        map.put("requester",String.valueOf(MainActivity.user.getId()));
                        String res = WebUtil.loginsend(strUrl,map);
                        System.out.println(res);
                    }
                }).start();
            }
        });
        return root;
    }

    private void init(View view){
        textView_begin = view.findViewById(R.id.textView_leave_begin);
        textView_end = view.findViewById(R.id.textView_leave_end);
        button = view.findViewById(R.id.button_leave);
    }
}
