package com.els.myapplication.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.els.myapplication.R;
import com.els.myapplication.bean.MyItem;
import com.els.myapplication.config.MyApplication;
import com.els.myapplication.ui.main.activity.MainActivity;

public class MyAdapter extends ListAdapter<MyItem,MyAdapter.MyViewHolder> {

    public MyAdapter(){
        super(new DiffUtil.ItemCallback<MyItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull MyItem oldItem, @NonNull MyItem newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull MyItem oldItem, @NonNull MyItem newItem) {
                return false;
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_user,parent,false);
        final MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.textView.getText().toString()){

                    case "设置": {
                        mynavigation(R.id.action_navigation_user_to_employeeFragment);
                        break;
                    }
                    case "请假": {
                        mynavigation(R.id.action_navigation_user_to_leaveFragment);
                        break;
                    }
                    case "请假管理": {
                        mynavigation(R.id.action_navigation_user_to_manageLeaveFragment);
                        break;
                    }
                    case "通知": {
                        mynavigation(R.id.action_navigation_user_to_infromFragment);
                        break;
                    }
                    case "项目": {
                        mynavigation(R.id.action_navigation_user_to_projectHomeFragment);
                        break;
                    }
                    case "创建项目": {
                        mynavigation(R.id.action_projectHomeFragment_to_projectCreateFragment);
                        break;
                    }
                    case "管理项目": {
                        mynavigation(R.id.action_projectHomeFragment_to_projectManageFragment);
                        break;
                    }
                    case  "设备" : {
                        mynavigation(R.id.action_navigation_user_to_equipmentHomeFragment);
                        break;
                    }
                    case  "添加设备" : {
                        mynavigation(R.id.action_equipmentHomeFragment_to_equipmentCreateFragment);
                        break;
                    }
                    case "管理设备" : {
                        mynavigation(R.id.action_equipmentHomeFragment_to_equipmentManageFragment);
                        break;
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyItem myItem = getItem(position);
        Resources res = MyApplication.getContext().getResources();
        holder.textView.setText(myItem.getText());
        holder.imageViewright.setImageDrawable( ResourcesCompat.getDrawable(res,myItem.getDrawable_right(),null));
        holder.imageViewleft.setImageDrawable(ResourcesCompat.getDrawable(res,myItem.getDrawable_left(),null));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageViewleft,imageViewright;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_item);
            imageViewleft = itemView.findViewById(R.id.imageView_item_left);
            imageViewright = itemView.findViewById(R.id.imageView_item_right);
        }
    }

    private void mynavigation(int i) {
        NavController navController = Navigation.findNavController(MainActivity.activity,R.id.nav_host_fragment);
        navController.navigate(i);
    }

}
