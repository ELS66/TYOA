package com.els.myapplication.ui.main.project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.Project
import com.els.myapplication.databinding.ActivityMapBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MapActivity : BaseActivity(),AMap.OnInfoWindowClickListener {
    private lateinit var binding: ActivityMapBinding
    private lateinit var aMap: AMap
    private var projectList : MutableList<Project> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading()
        binding.map.onCreate(savedInstanceState)
        aMap = binding.map.map
        val latLng = LatLng(34.72468,113.6401)
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,6F))
        aMap.setOnInfoWindowClickListener(this)
        initActionBar("项目详情",true)
        initData()
    }

    private fun initData() {
        val map: HashMap<String, Any> = HashMap()
        map["is"] = "2"
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.getproject(map)
                if (res.code == 200) {
                    projectList = res.data
                    projectList.forEach {
                        val latLng = LatLng(it.latitude.toDouble(),it.longitude.toDouble())
                        val marker = aMap.addMarker(MarkerOptions().position(latLng).title(it.name))
                        marker.showInfoWindow()
                    }
                }
                dismiss()
            } catch (e : Exception) {
                e.printStackTrace()
                dismiss()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_menu, menu)
        val item_add = menu.findItem(R.id.item_map_add)
        item_add.setOnMenuItemClickListener {
                startActivity(Intent(this@MapActivity, MapAddActivity::class.java))
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onInfoWindowClick(p0: Marker) {
        val intent = Intent(this,ProjectShowActivity::class.java)
        intent.putExtra("name",p0.title)
        startActivity(intent)
    }

}