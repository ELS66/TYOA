package com.els.myapplication.adapter;

import android.content.Intent;
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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.els.myapplication.App;
import com.els.myapplication.R;
import com.els.myapplication.bean.MyItem;
import com.els.myapplication.ui.other.InformActivity;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.ui.main.equipment.EquipmentCreateActivity;
import com.els.myapplication.ui.main.equipment.EquipmentHomeActivity;
import com.els.myapplication.ui.main.equipment.EquipmentManageActivity;
import com.els.myapplication.ui.main.personnel.PersonnelActivity;
import com.els.myapplication.ui.main.personnel.PersonnelAddActivity;
import com.els.myapplication.ui.main.project.MapActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyAdapter extends ListAdapter<MyItem,MyAdapter.MyViewHolder> {

    MyListener myListener;

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
                        Intent intent = new Intent(App.context, InformActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK );
                        App.context.startActivity(intent);
                        break;
                    }
                    case "项目": {
                        Intent intent = new Intent(MainActivity.activity,MapActivity.class);
                        MainActivity.activity.startActivity(intent);
                        break;
                    }
                   /* case "创建项目": {
                        mynavigation(R.id.action_projectHomeFragment_to_projectCreateFragment);
                        break;
                    }
                    case "管理项目": {
                        mynavigation(R.id.action_projectHomeFragment_to_projectManageFragment);
                        break;
                    }*/
                    case  "设备" : {
                        Intent intent = new Intent(MainActivity.activity, EquipmentHomeActivity.class);
                        MainActivity.activity.startActivity(intent);
                        break;
                    }
                    case  "添加设备" : {
                        Intent intent = new Intent(App.context, EquipmentCreateActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK );
                        App.context.startActivity(intent);
                        break;
                    }
                    case "管理设备" : {
                        Intent intent = new Intent(App.context, EquipmentManageActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK );
                        App.context.startActivity(intent);
                        break;
                    }
                    case "人事" : {
                        Intent intent = new Intent(App.context, PersonnelActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        App.context.startActivity(intent);
                        break;
                    }
                    case "添加员工" : {
                        Intent intent = new Intent(App.context, PersonnelAddActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        App.context.startActivity(intent);
                        break;
                    }
                    case  "删除员工" : {
                        myListener.onMyListener(0);
                        break;
                    }
                    case "管理权限" : {
                        myListener.onMyListener(1);
                        break;
                    }
                    case "管理员账号" : {
                        myListener.onMyListener(2);
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
        Resources res = App.context.getResources();
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

    public interface MyListener {
        public void onMyListener(int is);
    }

    public void setOnMyClickListener(MyListener myListener) {
        this.myListener = myListener;
    }

}
