package com.els.myapplication.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.els.myapplication.R
import com.els.myapplication.bean.Notice

class NoticeAdapter : BaseQuickAdapter<Notice.Data,BaseViewHolder>(R.layout.item_home_notice) {
    override fun convert(holder: BaseViewHolder, item: Notice.Data) {
        holder.itemView.findViewById<TextView>(R.id.tvItemHomeNews).text = item.title
    }

}