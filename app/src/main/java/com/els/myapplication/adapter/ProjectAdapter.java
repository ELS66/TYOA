package com.els.myapplication.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.els.myapplication.R;
import com.els.myapplication.bean.Project;
import com.els.myapplication.ui.main.activity.MainActivity;
import com.els.myapplication.ui.main.fragment.ProjectShowFragment;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.InnerHolder> {

    private List<Project> list;

    public ProjectAdapter(List<Project> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ProjectAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_project,parent,false);
        final InnerHolder holder = new InnerHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",holder.id.getText().toString());
                NavController navController = Navigation.findNavController(MainActivity.activity,R.id.nav_host_fragment);
                navController.navigate(R.id.action_projectManageFragment_to_projectShowFragment,bundle);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.InnerHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        private TextView name,id,startdate,manage;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textView_item_project_id);
            name = itemView.findViewById(R.id.textView_item_project_name);
            manage = itemView.findViewById(R.id.textView_item_project_manage);
            startdate = itemView.findViewById(R.id.textView_item_project_startdate);
        }

        public void setData(Project project) {
            id.setText(String.valueOf(project.getUid()));
            name.setText(project.getName());
            manage.setText(project.getManagement());
            startdate.setText(project.getStartdate());
        }
    }
}
