package com.els.myapplication.ui.leave

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.els.myapplication.App
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.adapter.LeaveManageAdapter
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.Leave
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.ActivityLeaveManageBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.utils.ShpUtil
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class LeaveManageActivity : BaseActivity() {

    private lateinit var binding: ActivityLeaveManageBinding
    val shp = ShpUtil(App.context,"login")
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaveManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("请假管理",true)
        loading()
        init()
    }

//    val handler : Handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            when(msg.what) {
//                0 -> {
//                    val gson = Gson()
//                    val leavelist = gson.fromJson<MutableList<Leave>>(msg.obj.toString(),object : TypeToken<MutableList<Leave>>(){}.type)
//                    binding.rvLeaveManage.layoutManager = LinearLayoutManager(this@LeaveManageActivity)
//                    val adapter = LeaveManageAdapter()
//                    binding.rvLeaveManage.adapter = adapter
//                    adapter.setNewInstance(leavelist)
//                    adapter.addChildClickViewIds(R.id.iv_true)
//                    adapter.addChildClickViewIds(R.id.iv_false)
//                    adapter.setOnItemChildClickListener { adapter, view, position ->
//                        when(view.id) {
//                            R.id.iv_true -> {
//                                sendTrue(leavelist[position].id.toString())
//                            }
//                            R.id.iv_false -> {
//                                sendFalse(leavelist[position].id.toString())
//                            }
//                        }
//                    }
//                    dismiss()
//                }
//                1 -> {
//                    dismiss()
//                    if (msg.obj.toString() == "1") {
//                        "批准成功！".showToast()
//                        finish()
//                    } else {
//                        "批准失败，请重试".showToast()
//                    }
//                }
//                2 -> {
//                    dismiss()
//                    if (msg.obj.toString() == "1") {
//                        "批准成功！".showToast()
//                        finish()
//                    } else {
//                        "批准失败，请重试".showToast()
//                    }
//                }
//            }
//        }
//    }



    fun init() {
        val map : HashMap<String,Any> = HashMap()
        map["is"] = "1"
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.leavemanage(map)
                if (res.code == 200) {
                    val leavelist = res.data
                    binding.rvLeaveManage.layoutManager = LinearLayoutManager(this@LeaveManageActivity)
                    val adapter = LeaveManageAdapter()
                    binding.rvLeaveManage.adapter = adapter
                    adapter.setNewInstance(leavelist)
                    adapter.addChildClickViewIds(R.id.iv_true)
                    adapter.addChildClickViewIds(R.id.iv_false)
                    adapter.setOnItemChildClickListener { adapter, view, position ->
                        when(view.id) {
                            R.id.iv_true -> {
                                sendTrue(leavelist[position].id.toString())
                            }
                            R.id.iv_false -> {
                                sendFalse(leavelist[position].id.toString())
                            }
                        }
                    }
                    dismiss()
                }
            } catch (e : Exception) {
                e.printStackTrace()
                dismiss()
            }
        }
//        thread {
//            val strUrl = Constant.WEB_ADDRESS+"/leave"
//            val map : HashMap<String,String> = HashMap()
//            map["is"] = "1"
//            val res = WebUtil.loginsend(strUrl,map)
//            val message = Message()
//            message.what = 0
//            message.obj = res
//            handler.sendMessage(message)
//        }
    }

    fun sendTrue(id : String) {
        loading()
        val map : HashMap<String,Any> = HashMap()
        val str = shp.load("user")
        val user = gson.fromJson<User>(str,User::class.java)
        val api = ApiRetrofit().getApiService()
        map["is"] = "2"
        map["id"] = id
        map["handler"] = user.username
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.leavemanagetrue(map)
                dismiss()
                if (res.code == 200) {
                    "批准成功！".showToast()
                    finish()
                } else {
                    "批准失败，请重试".showToast()
                }
            } catch (e : Exception) {
                e.printStackTrace()
                dismiss()
                "批准失败，请重试".showToast()
            }
        }
//        thread {
//            val strUrl = Constant.WEB_ADDRESS + "/leave"
//            val map : HashMap<String,String> = HashMap()
//            val str = shp.load("user")
//            val user = gson.fromJson<User>(str,User::class.java)
//            map["is"] = "2"
//            map["id"] = id
//            map["handler"] = user.username
//            val res = WebUtil.loginsend(strUrl,map)
//            val message = Message()
//            message.what = 1;
//            message.obj = res;
//            handler.sendMessage(message)
//        }
    }

    fun sendFalse(id : String) {
        val dialogView : View = LayoutInflater.from(this).inflate(R.layout.view_leave,null)
        val buider = AlertDialog.Builder(this)
                .setTitle("回复")
                .setView(dialogView)
                .create()
        val editText = dialogView.findViewById<EditText>(R.id.et_leave_view)
        val tv_button = dialogView.findViewById<TextView>(R.id.tv_leave_add)
        tv_button.setOnClickListener {
            if (editText.text.toString().trim().isEmpty()) {
                "请输入内容！".showToast()
                return@setOnClickListener
            }
            val str = shp.load("user")
            val user = gson.fromJson<User>(str,User::class.java)
            val api = ApiRetrofit().getApiService()
            val map : HashMap<String,Any> = HashMap()
            map["is"] = "3"
            map["id"] = id
            map["handler"] = user.username
            map["mess"] = editText.text.toString().trim()
            Log.e(Constant.TAG, "sendFalse: " + map["mess"] )
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res = api.leavemanagefalse(map)
                    dismiss()
                    if (res.code == 200) {
                        "批准成功！".showToast()
                        finish()
                    } else {
                        "批准失败，请重试".showToast()
                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                    dismiss()
                    "批准失败，请重试".showToast()
                }
            }
//            thread {
//                val strUrl = Constant.WEB_ADDRESS + "/leave"
//                val map : HashMap<String,String> = HashMap()
//                map["is"] = "3"
//                map["id"] = id
//                map["handler"] = user.username
//                map["mess"] = editText.text.toString().trim()
//                val res = WebUtil.loginsend(strUrl,map)
//                val message = Message()
//                message.what = 2
//                message.obj = res
//                handler.sendMessage(message)
//            }
            loading()
            buider.dismiss()
        }
        buider.show()
    }
}