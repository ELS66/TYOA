package com.els.myapplication.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.els.myapplication.App
import com.els.myapplication.R
import com.els.myapplication.bean.MeItem
import com.els.myapplication.showToast
import com.els.myapplication.ui.leave.LeaveActivity
import com.els.myapplication.ui.leave.LeaveManageActivity
import com.els.myapplication.ui.other.ChanagePassActivity
import com.els.myapplication.ui.other.FeedBackActivity
import com.els.myapplication.ui.other.InformActivity
import com.els.myapplication.ui.main.equipment.EquipmentManageActivity
import com.els.myapplication.ui.main.personnel.PersonnelActivity
import com.els.myapplication.ui.main.personnel.RootManageActivity
import com.els.myapplication.ui.main.project.MapActivity
import com.els.myapplication.ui.other.NoticeActivity
import com.els.myapplication.ui.main.equipment.EquipmentCreateActivity as EquipmentCreateActivity
import kotlin.jvm.java as java

class MeAdapter() : BaseQuickAdapter<MeItem, BaseViewHolder>(R.layout.item_me) {
    override fun convert(holder: BaseViewHolder, item: MeItem) {
        holder.setText(R.id.tvMeName,item.name)
        holder.setImageResource(R.id.imgMe,item.drawable_id)
        holder.itemView.setOnClickListener {
            when(item.id) {
                //权限
                0 -> {
                    context.startActivity(Intent(context,RootManageActivity::class.java))
                }
                //请假
                1 -> {
                    context.startActivity(Intent(context,LeaveActivity::class.java))
                }
                //项目
                2 -> {
                    context.startActivity(Intent(context,MapActivity::class.java))
                }
                //财务申请
                3 -> {
                    "功能暂未开放".showToast()
                }
                //修改密码
                4 -> {
                    context.startActivity(Intent(context,ChanagePassActivity::class.java))
                }
                //反馈建议
                5 -> {
                    context.startActivity(Intent(context,FeedBackActivity::class.java))
                }
                //请假管理
                6 -> {
                    context.startActivity(Intent(context,LeaveManageActivity::class.java))
                }
                //通知
                7 -> {
                    context.startActivity(Intent(context,InformActivity::class.java))
                }
                //人事
                8 -> {
                    context.startActivity(Intent(context,PersonnelActivity::class.java))
                }
                //项目管理
                9 -> {
                    context.startActivity(Intent(context,MapActivity::class.java))
                }
                //设备管理
                10 -> {
                    context.startActivity(Intent(context,EquipmentManageActivity::class.java))
                }
                //财务审批
                11 -> {
                    "功能暂未开放".showToast()
                }
                //公告
                12 -> {
                    context.startActivity(Intent(context,NoticeActivity::class.java))
                }
                //添加设备
                13 -> {
                    context.startActivity(Intent(context,EquipmentCreateActivity::class.java))
                }
            }
        }
    }
    

    
}