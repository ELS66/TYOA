package com.els.myapplication.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.els.myapplication.R
import com.els.myapplication.bean.Leave

class LeaveManageAdapter : BaseQuickAdapter<Leave,BaseViewHolder>(R.layout.item_leave_manage){
    override fun convert(holder: BaseViewHolder, item: Leave) {
        holder.setText(R.id.tv_name,item.name)
                .setText(R.id.tv_begin,item.begin)
                .setText(R.id.tv_end,item.end)
    }
}