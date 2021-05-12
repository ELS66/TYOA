package com.els.myapplication.ui.main.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.User
import com.els.myapplication.push.MyXGRecevier
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.utils.ShpUtil
import com.els.myapplication.utils.WebUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.tencent.android.tpush.XGPushClickedResult
import java.util.*

class MainActivity : BaseActivity() {
    var appBarConfiguration: AppBarConfiguration? = null
    private val toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val shpUtil = ShpUtil(this@MainActivity, "login")
        val gson = Gson()
        user = gson.fromJson(shpUtil.load("user"),User::class.java)
        appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_function, R.id.meFragment)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navView, navController)
        initActionBar("同业电力", false)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(this, "扫码失败！", Toast.LENGTH_SHORT).show()
            } else {
                val result = intentResult.contents
                val bundle = Bundle()
                bundle.putString("uid", result)
                val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
                navController.navigate(R.id.action_navigation_home_to_equipmentShowFragment, bundle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val myXGRecevier = MyXGRecevier()
        val xgPushClickedResult = XGPushClickedResult()
        myXGRecevier.onNotificationClickedResult(this, xgPushClickedResult)
    }

    override fun onDestroy() {
        super.onDestroy()
        val shpUtil = ShpUtil(this, "login")
        if (shpUtil.load("login") == "false") {
            Thread(Runnable {
                val strUrl = Constant.WEB_ADDRESS + "/exit"
                val map: MutableMap<String, String> = HashMap()
                map["id"] = user!!.id.toString()
                WebUtil.loginsend(strUrl, map)
            }).start()
        }
    }

    companion object {
        @JvmField
        var activity: Activity? = null
        @JvmField
        var user: User? = null
        @JvmField
        var tv_title: TextView? = null
    }
}