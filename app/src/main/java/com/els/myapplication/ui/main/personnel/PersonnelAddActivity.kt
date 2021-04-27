package com.els.myapplication.ui.main.personnel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.databinding.ActivityPersonnelAddBinding
import com.els.myapplication.showToast
import com.els.myapplication.utils.WebUtil
import kotlin.concurrent.thread

class PersonnelAddActivity : BaseActivity() {

    lateinit var binding: ActivityPersonnelAddBinding
    var root = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonnelAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("添加员工",true)
        init()
    }

    val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                0 -> {
                    val res = msg.obj.toString().toInt()
                    if (res == -1 || res == 0){
                        "添加失败，请重试！".showToast()
                    } else {
                        val alertDialog = AlertDialog.Builder(this@PersonnelAddActivity)
                                .setTitle("创建成功！")
                                .setMessage("员工编号为：$res\n初始密码为：123456")
                                .setPositiveButton("确定") { dialogInterface, i -> finish() }
                                .create()
                        alertDialog.show()
                    }
                }
            }
        }
    }

    fun init() {
        val items = arrayOf("管理人员", "正式员工", "临时员工")
        val adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,items)
        binding.spinnerPersonnelCreateRoot.adapter = adapter
        binding.spinnerPersonnelCreateRoot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                root = -1
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                root = p2
            }

        }
        binding.tvPersonnelAdd.singleClick {
            if (binding.etPersonnelCreateName.text.toString().trim().isEmpty()) {
                "请输入员工姓名".showToast()
                return@singleClick
            }
            thread {
                val strUrl = Constant.WEB_ADDRESS + "/personnel"
                val map : MutableMap<String,String> = HashMap()
                map["is"] = "0"
                map["name"] = binding.etPersonnelCreateName.text.toString().trim()
                map["root"] = root.toString()
                val res = WebUtil.loginsend(strUrl,map)
                val message = Message()
                message.what = 0
                message.obj = res
                handler.sendMessage(message)
            }
        }
    }
}