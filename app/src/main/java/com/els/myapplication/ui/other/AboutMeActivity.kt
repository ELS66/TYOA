package com.els.myapplication.ui.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.databinding.ActivityAboutMeBinding

class AboutMeActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("关于我们",true)
        binding.tvVersion.text = "v ${getVersion()}"
    }

    private fun getVersion(): String? {
        var version: String? = ""
        val packageInfo = packageManager
                ?.getPackageInfo(packageName, 0)
        version = packageInfo?.versionName
        return version
    }
}