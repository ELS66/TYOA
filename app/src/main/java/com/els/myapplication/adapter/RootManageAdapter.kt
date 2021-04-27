package com.els.myapplication.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.els.myapplication.R
import com.els.myapplication.bean.MeItem

class RootManageAdapter : BaseQuickAdapter<MeItem,BaseViewHolder>(R.layout.item_root) {
    override fun convert(holder: BaseViewHolder, item: MeItem) {
        holder.setImageResource(R.id.iv_root,item.drawable_id)
                .setText(R.id.tv_root,item.name)

    }


}