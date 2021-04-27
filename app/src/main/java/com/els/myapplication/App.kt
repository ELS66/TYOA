package com.els.myapplication

import android.app.Application
import android.content.Context
import android.widget.Toast

class App : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }



}