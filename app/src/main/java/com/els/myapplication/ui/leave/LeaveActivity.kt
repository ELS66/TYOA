package com.els.myapplication.ui.leave

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.els.myapplication.Constant
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.ActivityLeaveBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.utils.MyUtil
import com.els.myapplication.utils.ShpUtil
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class LeaveActivity : BaseActivity() {

    private lateinit var binding: ActivityLeaveBinding
    private  var datestart : Date? = null
    private  var dateend : Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("请假",true)
        init()
    }

//    val handler : Handler = object  : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            when(msg.what) {
//                0 -> {
//                    dismiss()
//                    if (msg.obj.toString() != "0") {
//                        "提交成功！".showToast()
//                        finish()
//                    } else {
//                        "提交失败!".showToast()
//                        Log.e(Constant.TAG,msg.obj.toString() )
//                    }
//
//                }
//            }
//        }
//    }

    fun init() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.textViewLeaveBegin.setOnClickListener {
            val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                binding.textViewLeaveBegin.text = simpleDateFormat.format(date)
                datestart = date
            }).build().show()
        }
        binding.textViewLeaveEnd.setOnClickListener {
            val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                binding.textViewLeaveEnd.text = simpleDateFormat.format(date)
                dateend = date
            }).build().show()
        }
        binding.buttonLeave.setOnClickListener {
            if (datestart == null || dateend == null ) {
                "请选择请假日期".showToast()
                return@setOnClickListener
            }
            loading()
            val sdf_year = SimpleDateFormat("yyyy")
            val year_begin = sdf_year.format(datestart).toInt()
            val year_end = sdf_year.format(dateend).toInt()
            val day: Int
            if (year_begin == year_end) {
                val sdf_Day = SimpleDateFormat("D")
                val day_begin = sdf_Day.format(datestart).toInt()
                val day_end = sdf_Day.format(dateend).toInt()
                day = day_end - day_begin + 1
            } else {
                var day_one = 365
                if (MyUtil.bissextile(year_begin)) {
                    day_one = 366
                }
                val sdf_Day = SimpleDateFormat("D")
                day = day_one - sdf_Day.format(datestart).toInt() + sdf_Day.format(dateend).toInt() + 1
            }
            val shpUtil = ShpUtil(this, "login")
            val gson = Gson()
            println(shpUtil.load("user"))
            val user = gson.fromJson(shpUtil.load("user"), User::class.java)
            val map : HashMap<String,Any> = HashMap()
            map["is"] = "0"
            map["name"] = user.username
            map["begin"] = binding.textViewLeaveBegin.text.toString()
            map["end"] = binding.textViewLeaveEnd.text.toString()
            map["requester"] = user.id.toString()
            val api = ApiRetrofit().getApiService();
            CoroutineScope(Dispatchers.Main).launch {
                val res = api.leave(map)
                dismiss()
                if (res.code == 200) {
                    "提交成功！".showToast()
                    finish()
                } else {
                    "提交失败!".showToast()
                }
            }
//            thread {
//                val shpUtil = ShpUtil(this, "login")
//                val gson = Gson()
//                println(shpUtil.load("user"))
//                val user = gson.fromJson(shpUtil.load("user"), User::class.java)
//                val strUrl = Constant.WEB_ADDRESS + "/leave"
//                val map : HashMap<String,String> = HashMap()
//                map["is"] = "0"
//                map["name"] = user.username
//                map["begin"] = binding.textViewLeaveBegin.text.toString()
//                map["end"] = binding.textViewLeaveEnd.text.toString()
//                map["requester"] = user.id.toString()
//                val res = WebUtil.loginsend(strUrl,map)
//                val message = Message()
//                message.what = 0
//                message.obj = res
//                handler.sendMessage(message)
//            }
        }
    }
}