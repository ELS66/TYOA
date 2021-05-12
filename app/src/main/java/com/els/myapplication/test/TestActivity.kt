package com.els.myapplication.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.els.myapplication.databinding.ActivityTestBinding
import com.els.myapplication.utils.RsaEncryptUtil

class TestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{
            val a = "ELSELS"
            val res = RsaEncryptUtil.encryptByPublicKey(a)
            Log.e("TAG", "onCreate: "+res )
        }
    }
}