package com.els.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.els.myapplication.R
import com.els.myapplication.bean.Financial

class ProjectFinancialAdapter (financialList: List<Financial>) : RecyclerView.Adapter<ProjectFinancialAdapter.ViewHolder>() {

    private val financialList: List<Financial> = financialList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_financial_layout,parent,false)
        val holder = ViewHolder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        if (financialList.isNotEmpty()) {
            return financialList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(financialList[position])
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val item = itemView
        private val tv_id = itemView.findViewById<TextView>(R.id.tv_financial_id)
        private val tv_name = itemView.findViewById<TextView>(R.id.tv_financial_name)
        private val tv_money = itemView.findViewById<TextView>(R.id.tv_financial_money)
        private val tv_date = itemView.findViewById<TextView>(R.id.tv_financial_date)

        fun setData(financial: Financial) {
            tv_id.text = financial.id.toString()
            tv_name.text = financial.name
            tv_money.text = financial.money.toString()
            tv_date.text = financial.date
        }
    }
}