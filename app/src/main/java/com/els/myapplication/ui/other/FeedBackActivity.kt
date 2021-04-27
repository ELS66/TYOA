package com.els.myapplication.ui.other

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.els.myapplication.App
import com.els.myapplication.Constant
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.ActivityFeedBackBinding
import com.els.myapplication.showToast
import com.els.myapplication.utils.ShpUtil
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import kotlin.concurrent.thread

class FeedBackActivity : BaseActivity() {

    private lateinit var binding: ActivityFeedBackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("反馈意见",true)
        init()
    }

    val handler : Handler = object  : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> {
                    dismiss()
                    if (msg.obj.toString() == "1") {
                        "提交成功".showToast()
                        finish()
                    } else {
                        "提交失败".showToast()
                    }
                }
            }
        }
    }

    fun init() {
        binding.tvPost.setOnClickListener {
            if (binding.etFeedBack.text.toString().trim().isEmpty()) {
                "请先填写反馈意见".showToast()
                return@setOnClickListener
            }
            loading()
            val shpUtil = ShpUtil(App.context,"login")
            val str = shpUtil.load("user")
            val gson = Gson()
            val user = gson.fromJson<User>(str,User::class.java)
            thread {
                val strUrl = Constant.WEB_ADDRESS + "/feed"
                val map : HashMap<String,String> = HashMap()
                map["name"] = user.username
                map["feedback"] = binding.etFeedBack.text.toString().trim()
                val res = WebUtil.loginsend(strUrl,map)
                val message = Message()
                message.what = 0
                message.obj = res
                Log.e(Constant.TAG,res)
                handler.sendMessage(message)
            }
        }
    }
}