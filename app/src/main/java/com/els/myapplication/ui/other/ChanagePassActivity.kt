package com.els.myapplication.ui.other

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Process
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.els.myapplication.App
import com.els.myapplication.Constant
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.ActivityChanagePassBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.utils.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ChanagePassActivity : BaseActivity() {

    private lateinit var binding: ActivityChanagePassBinding
    private val shp = ShpUtil(App.context,"login")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChanagePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("修改密码",true)
        val str = shp.load("user")
        val gson = Gson()
        val user = gson.fromJson<User>(str,User::class.java)
        init(user.password,user.username)
    }

    fun init(pass : String,name : String) {
        binding.sure.setOnClickListener {
            if (binding.edNowPass.text.isEmpty()) {
                "请先填写当前密码".showToast()
                return@setOnClickListener
            }
            if (binding.edNewPass.text.isEmpty()) {
                "请先填写新密码".showToast()
                return@setOnClickListener
            }
            if (binding.edNewPass2.text.isEmpty()) {
                "请先确认新密码".showToast()
                return@setOnClickListener
            }
            if (binding.edNowPass.text.toString().trim() == binding.edNewPass.text.toString().trim()) {
                "当前新密码与原先密码一致,请重新输入新密码".showToast()
                return@setOnClickListener
            }
            if(binding.edNewPass.text.toString().trim() != binding.edNewPass2.text.toString().trim()) {
                "当前输入的新密码不一致,请重新输入".showToast()
            }
            if (binding.edNewPass.text.toString().trim().length < 8) {
                "请输入8位以上的密码".showToast()
                return@setOnClickListener
            }
            loading()
            val api = ApiRetrofit().getApiService()
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val map: HashMap<String, Any> = HashMap()
                    val key = AesUtil.generateKey()
                    val rsa = RsaEncryptUtil.encryptByPublicKey(key)
                    val newpass = AesUtil.encrypt(key,binding.edNewPass.text.toString().trim())
                    val oldpass = AesUtil.encrypt(key,binding.edNowPass.text.toString().trim())
                    map["user"] = name
                    map["old"] = oldpass
                    map["new"] = newpass
                    map["rsa"] = rsa
                    val res = api.changepass(map)
                    dismiss()
                    when(res.code) {
                        200 -> {
                            shp.clear()
                            AlertDialog.Builder(this@ChanagePassActivity)
                                    .setTitle("修改成功")
                                    .setMessage("即将重启应用")
                                    .setNegativeButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                                        restartApplication(this@ChanagePassActivity)
                                    }).show()
                        }
                        2 -> {
                            "原密码错误，请重试".showToast()
                        }
                        else -> {
                            "修改失败，请重试!".showToast()
                        }
                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    fun restartApplication(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        Process.killProcess(Process.myPid())
    }

}