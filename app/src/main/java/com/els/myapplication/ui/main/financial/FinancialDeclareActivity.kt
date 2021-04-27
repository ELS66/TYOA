package com.els.myapplication.ui.main.financial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.databinding.ActivityFinancialDeclareBinding
import com.els.myapplication.showToast
import com.els.myapplication.utils.ShpUtil
import com.els.myapplication.utils.WebUtil
import kotlin.concurrent.thread

class FinancialDeclareActivity : BaseActivity() {

    lateinit var binding: ActivityFinancialDeclareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinancialDeclareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("财务申请",true)
        val bundle = intent.extras
        if (bundle != null) {
            val project = bundle.get("project").toString()
            init(project)
        }
    }

//    val handler : Handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            when(msg.what) {
//                0 -> {
//                    if (msg.obj.toString() == "1") {
//                        dismiss()
//                        "提交成功！".showToast()
//                        finish()
//                    } else {
//                        dismiss()
//                        "提交失败！".showToast()
//                    }
//                }
//            }
//        }
//    }

    fun init(project : String) {
        binding.tvButton.singleClick {
            if (binding.etFinancialName.text.toString().trim().isEmpty()) {
                "请输入申报简介！".showToast()
                return@singleClick
            }
            if (binding.etFinancialTitle.text.toString().trim().isEmpty()) {
                "请输入申报详情！".showToast()
                return@singleClick
            }
            if (binding.etFinancialMoney.text.toString().trim().isEmpty()) {
                "请输入申报金额！".showToast()
                return@singleClick
            }
            loading()
//            thread {
//                val shpUtil = ShpUtil(this,"login")
//                val strUrl = Constant.WEB_ADDRESS + "/financial"
//                val map : MutableMap<String,String> = HashMap()
//                map["is"] = "0"
//                map["name"] = binding.etFinancialName.text.toString().trim()
//                map["title"] = binding.etFinancialTitle.text.toString().trim()
//                map["money"] = binding.etFinancialMoney.text.toString().trim()
//                map["requester"] = shpUtil.load("name")
//                map["project"] = project
//                val res = WebUtil.loginsend(strUrl,map)
//                Log.e(Constant.TAG, res )
//                val message = Message()
//                message.what = 0
//                message.obj = res
//                handler.sendMessage(message)
//            }
        }
        binding.tvFinancialDeclare.text = project
    }
}