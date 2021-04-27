package com.els.myapplication.ui.main.equipment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.els.myapplication.R
import com.els.myapplication.adapter.MyAdapter
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.MyItem

class EquipmentHomeActivity : BaseActivity() {

    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_home)
        initActionBar("设备",true)
        recyclerView = findViewById(R.id.rv_equipment_home)
        val list = mutableListOf<MyItem>(MyItem("添加设备",R.drawable.item_set,R.drawable.item_to),MyItem("管理设备",R.drawable.item_set,R.drawable.item_to))
        val myAdapter = MyAdapter()
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter.submitList(list)
    }
}