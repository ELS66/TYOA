package com.els.myapplication.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.els.myapplication.R;
import com.els.myapplication.bean.MessItem;
import com.els.myapplication.ui.main.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.InnerHolder> {

    private List<MessItem> mData;


    public MessAdapter(List<MessItem> mData){
        this.mData = mData;
    }

    @NonNull
    @Override
    public MessAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_mess,parent,false);
        final InnerHolder holder = new InnerHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.title.getText().toString()) {
                    case "通知": {
                        Intent intent = new Intent();
                        Uri uri = Uri.parse("xgscheme://com.tpns.push/notify_detail?param1=通知&param2=" + holder.id.getText().toString() + "&param3=" + "0");
                        intent.setData(uri);
                        MainActivity.activity.startActivity(intent);
                        break;
                    }
                    case "请假": {
                        Intent intent = new Intent();
                        Uri uri = Uri.parse("xgscheme://com.tpns.push/notify_detail?param1=请假&param2=" + holder.id.getText().toString() + "&param3=" + "0");
                        intent.setData(uri);
                        MainActivity.activity.startActivity(intent);
                        break;
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessAdapter.InnerHolder holder, int position) {
        try {
            holder.setData(mData.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title,content,date,id;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView_mess);
            title = itemView.findViewById(R.id.textView_mess_title);
            content = itemView.findViewById(R.id.textView_mess_content);
            date = itemView.findViewById(R.id.textView_mess_date);
            id = itemView.findViewById(R.id.textView_mess_id);
        }

        public void setData(MessItem messItem) throws ParseException {
            icon.setImageResource(R.drawable.item_mess);
            title.setText(messItem.getTitle());
            content.setText(messItem.getContent());
            id.setText(String.valueOf(messItem.getId()));
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = simpleDateFormat1.parse(messItem.getDate());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            String str1 = simpleDateFormat2.format(date1);
            String str2 = simpleDateFormat2.format(new Date());
            if (str1.equals(str2)){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String str = simpleDateFormat.format(date1);
                date.setText(str);
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
                String str = simpleDateFormat.format(date1);
                date.setText(str);
            }
        }
    }
}
