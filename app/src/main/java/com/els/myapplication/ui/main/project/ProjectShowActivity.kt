package com.els.myapplication.ui.main.project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.adapter.ProjectFinancialAdapter
import com.els.myapplication.adapter.Project_EquipmentAdapter
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.Equipment
import com.els.myapplication.bean.Financial
import com.els.myapplication.bean.Project
import com.els.myapplication.databinding.ActivityProjectShowBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.ui.main.financial.FinancialDeclareActivity
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ProjectShowActivity : BaseActivity() {

    private lateinit var binding: ActivityProjectShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading()
        val str = intent.getStringExtra("name")
        if (str == null) {
            finish()
        } else {
            initActionBar(str,true)
            initData(str)
        }

    }

    private fun initData(string: String) {
        val map : HashMap<String,Any> = HashMap()
        map["is"] = "3"
        map["name"] = string
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.projectshow(map)
                if (res.code == 200) {
                    val gson = Gson()
                    val strings = res.data
                    val project = gson.fromJson<Project>(strings[0],object : TypeToken<Project>(){}.type)
                    val equipmentList : List<Equipment> = gson.fromJson<List<Equipment>>(strings[1],object : TypeToken<List<Equipment>>(){}.type)
                    if (strings[2] == "null") {
                        binding.projectShowLayout.visibility = View.GONE
                    } else {
                        val financialList : List<Financial> = gson.fromJson(strings[2],object : TypeToken<List<Financial>>(){}.type)
                        binding.rvProjectFinancial.layoutManager = LinearLayoutManager(this@ProjectShowActivity)
                        binding.rvProjectFinancial.adapter = ProjectFinancialAdapter(financialList)
                    }
                    binding.let {
                        it.tvProjectShowAddress.text = project.address
                        it.tvProjectShowEmployee.text = myUtil(project.employee)
                        it.tvProjectShowManage.text = myUtil(project.management)
                        it.rvProjectShow.layoutManager = LinearLayoutManager(this@ProjectShowActivity)
                        it.rvProjectShow.adapter = Project_EquipmentAdapter(equipmentList)
                    }
                }
                dismiss()
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }

        binding.imageView2.singleClick {
            val intent = Intent(this,FinancialDeclareActivity::class.java)
            intent.putExtra("project",string)
            startActivity(intent)
        }
    }

    fun myUtil(string: String) : String {
        val gson = Gson()
        val list = gson.fromJson<List<String>>(string,object : TypeToken<List<String>>(){}.type)
        val stringBuilder = StringBuilder()
        for (s in list) {
            stringBuilder.append(s)
            stringBuilder.append(",")
        }
        stringBuilder.delete(list.size-1,list.size-1)
        return stringBuilder.toString()
    }

}