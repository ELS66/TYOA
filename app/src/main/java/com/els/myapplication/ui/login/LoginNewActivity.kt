package com.els.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.ActivityLoginNewBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.utils.*
import com.google.gson.Gson
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushClickedResult
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginNewBinding
    var loading: LoadingPopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val shpUtil = ShpUtil(this,"login")
        if (shpUtil.load("login") == "true") {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (shpUtil.load("name") != "null" ) {
            binding.editTextLoginName.setText(shpUtil.load("name"))
        }
        binding.buttonLoginA.setOnClickListener {
            if (binding.editTextLoginName.text.toString().isEmpty()) {
                "请输入账号".showToast()
                return@setOnClickListener
            }
            if (binding.editTextLoginPass.text.toString().trim().isEmpty()) {
                "请输入密码".showToast()
                return@setOnClickListener
            }
            loading()
            XGPushConfig.enableDebug(this,true)
            XGPushManager.registerPush(this, object : XGIOperateCallback {
                override fun onSuccess(p0: Any?, p1: Int) {
                    val map: HashMap<String, Any> = HashMap()
                    val key = AesUtil.generateKey()
                    val rsa = RsaEncryptUtil.encryptByPublicKey(key)
                    val pass = AesUtil.encrypt(key, binding.editTextLoginPass.text.toString().trim())
                    map["user"] = binding.editTextLoginName.text.toString()
                    map["pass"] = pass
                    map["token"] = p0.toString()
                    map["rsa"] = rsa
                    val api = ApiRetrofit().getApiService()
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val res = api.login(map)
                            dismiss()
                            when (res.code) {
                                -1 -> "服务器出现问题，请重试".showToast()
                                1 -> "用户名错误，请重试".showToast()
                                2 -> "密码错误，请重试".showToast()
                                200 -> {
                                    val shpUtil = ShpUtil(this@LoginNewActivity, "login")
                                    if (binding.checkBoxLoginA.isChecked) {
                                        shpUtil.save("login", "true")
                                    } else {
                                        shpUtil.save("login", "false")
                                    }
                                    val gson = Gson()
                                    val user: User = res.data
                                    val str = gson.toJson(user)
                                    shpUtil.save("name", binding.editTextLoginName.text.toString())
                                    shpUtil.save("user", gson.toJson(res.data).toString())
                                    val intent = Intent(this@LoginNewActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } catch (e: Exception) {
                            dismiss()
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFail(p0: Any?, p1: Int, p2: String?) {
                    dismiss()
                    "网络连接错误！请检查网络或联系管理员".showToast()
                }

            })
        }
    }

    override fun onResume() {
        super.onResume()
        val clickedResult = XGPushClickedResult()
    }

    fun loading() {
        loading = XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(true)
                .hasStatusBarShadow(false)
                .hasShadowBg(false).asLoading()
        if (!loading?.isShow!!) {
            loading?.show()
        }
    }

    fun dismiss() {
        try {
            if (loading!!.isShow)
                loading?.dismiss()
            loading = null
        } catch (e: Exception) {

        }

    }

}