package com.els.myapplication.ui.main.equipment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.adapter.EquipmentAdapter
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.Equipment
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class EquipmentManageActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_manage)
        initActionBar("设备管理", true)
        loading()
        recyclerView = findViewById(R.id.rv_equipment_manage)
        val map: HashMap<String, Any> = HashMap()
        map["is"] = "1"
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.equipmentmanage(map)
                if (res.code == 200) {
                    val equipmentlist = res.data
                    val equipmentAdapter = EquipmentAdapter(equipmentlist)
                    recyclerView.layoutManager = LinearLayoutManager(this@EquipmentManageActivity)
                    recyclerView.adapter = equipmentAdapter
                } else {
                    "加载失败，请重试".showToast()
                }
                dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
                dismiss()
            }
        }
    }

}