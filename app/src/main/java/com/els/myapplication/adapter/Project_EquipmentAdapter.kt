package com.els.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.els.myapplication.App
import com.els.myapplication.R
import com.els.myapplication.bean.Equipment
import com.els.myapplication.showToast

class Project_EquipmentAdapter(equipmentList : List<Equipment>): RecyclerView.Adapter<Project_EquipmentAdapter.ViewHolder>() {

    private val equipmentLists : List<Equipment> = equipmentList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_project_equipment,parent,false)
        val holder = ViewHolder(itemView)
        holder.itemView.setOnLongClickListener {
            return@setOnLongClickListener false
        }
        return holder
    }

    override fun getItemCount(): Int {
        if (equipmentLists.isNotEmpty()) {
            return equipmentLists.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(equipmentLists[position])
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val mitem = itemView
        val tv_id = mitem.findViewById<TextView>(R.id.tv_item_project_equipment_id)
        val tv_name = mitem.findViewById<TextView>(R.id.tv_item_project_equipment_name)
        val tv_model = mitem.findViewById<TextView>(R.id.tv_item_project_equipment_model)

        fun setData(equipment: Equipment) {
            tv_name.text = equipment.name
            tv_id.text = equipment.uid.toString()
            tv_model.text = equipment.model
        }

    }
}