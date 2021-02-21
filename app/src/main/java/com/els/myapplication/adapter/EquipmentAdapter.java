package com.els.myapplication.adapter;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.els.myapplication.Constant;
import com.els.myapplication.R;
import com.els.myapplication.bean.Equipment;
import com.els.myapplication.bean.Project;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.utils.WebUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.InnerHolder> {

    private List<Equipment> equipmentList;
    public EquipmentAdapter(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;

    }

    @NonNull
    @Override
    public EquipmentAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_equipment,parent,false);
        final InnerHolder holder = new InnerHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",holder.textView_id.getText().toString());
                NavController navController = Navigation.findNavController(MainActivity.activity,R.id.nav_host_fragment);
                navController.navigate(R.id.action_equipmentManageFragment_to_equipmentShowFragment,bundle);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentAdapter.InnerHolder holder, int position) {
        holder.setData(equipmentList.get(position));
    }

    @Override
    public int getItemCount() {
        if (equipmentList.size() != 0) {
            return equipmentList.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        TextView textView_name,textView_id,textView_project;

        String string;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_equipment_item_name);
            textView_id = itemView.findViewById(R.id.textView_equipment_item_id);
            textView_project = itemView.findViewById(R.id.textView_equipment_manage_project);
        }

        public void setData(Equipment equipment) {
            textView_name.setText(equipment.getName());
            textView_id.setText(String.valueOf(equipment.getUid()));
            if (equipment.getProject().equals("null")) {
                textView_project.setText("空闲");
            } else {
                textView_project.setText(equipment.getProject());
            }

        }

    }

}
