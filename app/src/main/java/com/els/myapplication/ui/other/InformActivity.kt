package com.els.myapplication.ui.other

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AlertDialog
import com.els.myapplication.Constant
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.databinding.ActivityInformBinding
import com.els.myapplication.showToast
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import kotlin.concurrent.thread

class InformActivity : BaseActivity() {

    private lateinit var binding: ActivityInformBinding
    private val list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("通知",true)
        init()
    }

    val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                0 -> {
                    dismiss()
                    "发送成功!".showToast()
                }
                1 -> {
                    dismiss()
                    "发送失败!".showToast()
                }
                2 -> {
                    dismiss()
                    dialog(msg.obj.toString())
                }
                3 -> {
                    dismiss()
                    "发送成功!".showToast()
                    finish()
                }
            }
        }
    }

    private fun init() {
        binding.tvInformAll.singleClick {
            if (binding.etInform.text.toString().trim().isNullOrEmpty()) {
                "请输入内容！".showToast()
                return@singleClick
            }
            loading()
            thread {
                val strUrl = Constant.WEB_ADDRESS + "/inform"
                val map = HashMap<String,String>()
                map["is"] = "0"
                map["id"] = MainActivity.user!!.id.toString()
                map["name"] = MainActivity.user!!.username
                map["content"] = binding.etInform.text.toString()
                map["token"] = "all"
                val res = WebUtil.loginsend(strUrl,map)
                val message = Message()
                if (res.equals("true")){
                    message.what = 0
                } else{
                    message.what = 1
                }
                handler.sendMessage(message)
            }
        }
        binding.tvInformPart.singleClick {
            if (binding.etInform.text.toString().trim().isNullOrEmpty()) {
                "请输入内容！".showToast()
                return@singleClick
            }
            loading()
            thread {
                val strUrl = Constant.WEB_ADDRESS + "/inform"
                val map = HashMap<String,String>()
                map["is"] = "1"
                val res = WebUtil.loginsend(strUrl,map)
                val message = Message()
                message.what = 2
                message.obj = res
                handler.sendMessage(message)
            }
        }
    }

    fun dialog (str : String) {
        val gson = Gson()
        val strings : Array<String> = gson.fromJson(str,Array<String>::class.java)
        val dialog = AlertDialog.Builder(this@InformActivity)
        dialog.setTitle("选择人员")
        list.clear()
        dialog.setMultiChoiceItems(strings,null, DialogInterface.OnMultiChoiceClickListener { dialogInterface, i, b ->
            if (b) {
                list.add(strings[i])
            } else {
                list.remove(strings[i])
            }
        })
        dialog.setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
            if (list.size == 0) {
                "请至少选择一位人员".showToast()
                return@OnClickListener
            }
            thread {
                val strUrl = Constant.WEB_ADDRESS + "/inform"
                val map = HashMap<String,String>()
                map["is"] = "2"
                map["id"] = MainActivity.user!!.id.toString()
                map["list"] = gson.toJson(list)
                map["content"] = binding.etInform.text.toString()
                map["name"] = MainActivity.user!!.username
                val res = WebUtil.loginsend(strUrl,map)
                val message = Message()
                if (res.equals("true")) {
                    message.what = 3
                } else{
                    message.what = 1
                }
                handler.sendMessage(message)
            }
        })
        dialog.setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->

        })
        dialog.show()
    }
}