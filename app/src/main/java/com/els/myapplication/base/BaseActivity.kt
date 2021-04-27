package com.els.myapplication.base

import android.os.Bundle
import android.os.PersistableBundle
import android.service.autofill.OnClickAction
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.google.android.material.appbar.AppBarLayout
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseActivity : AppCompatActivity() {

    var loading: LoadingPopupView? = null
    var actionBar : ActionBar? = null
    private var hash : Int = 0
    private var lastClickTime : Long = 0
    private val time : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(Constant.TAG, javaClass.name)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initActionBar(title : String,hasBackButton : Boolean) {
        val appBar = findViewById<AppBarLayout>(R.id.appbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val textView = findViewById<TextView>(R.id.tv_toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        if (actionBar == null) {
            return
        }
        if (hasBackButton) {
            actionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar!!.setHomeButtonEnabled(true)
        } else {
            actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
            actionBar!!.setDisplayUseLogoEnabled(false)
        }
        textView.text = title
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init()
    }


    infix fun View.singleClick(clickAction: () -> Unit) {
        this.setOnClickListener {
            if (this.hashCode() != hash) {
                hash = this.hashCode()
                lastClickTime = System.currentTimeMillis()
                clickAction()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > time) {
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                }
            }
        }
    }


    fun loading() {
        loading = XPopup.Builder(this@BaseActivity)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}