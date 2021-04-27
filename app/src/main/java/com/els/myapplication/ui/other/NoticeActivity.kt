package com.els.myapplication.ui.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.els.myapplication.App
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.ActivityNoticeBinding
import com.els.myapplication.showToast
import com.els.myapplication.utils.ShpUtil
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import kotlin.concurrent.thread

class NoticeActivity : BaseActivity() {

    private lateinit var binding: ActivityNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("公告",true)
        init()
    }

    val handler : Handler = object  : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> {
                    dismiss()
                    if (msg.obj.toString() == "1") {
                        "发布成功".showToast()
                        finish()
                    } else {
                        "发布失败".showToast()
                    }
                }
            }
        }
    }

    fun init() {
        binding.tvPost.setOnClickListener {
            if (binding.etNotice.text.toString().trim().isEmpty()) {
                "请先填写发布内容".showToast()
                return@setOnClickListener
            }
            loading()
            val shpUtil = ShpUtil(App.context,"login")
            val str = shpUtil.load("user")
            val gson = Gson()
            val user = gson.fromJson<User>(str,User::class.java)
            thread {
                val strUrl = Constant.WEB_ADDRESS + "/notice"
                val map : HashMap<String,String> = HashMap()
                map["is"] = "0"
                map["mess"] = binding.etNotice.text.toString().trim()
                map["name"] = user.username
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