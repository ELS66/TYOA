package com.els.myapplication.ui.main.equipment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.SingleSpinnerListener
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.Equipment
import com.els.myapplication.databinding.ActivityEquipmentShowBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class EquipmentShowActivity : BaseActivity() {

    lateinit var binding: ActivityEquipmentShowBinding
    lateinit var old : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipmentShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            val uid = bundle.getString("uid").toString()
            initData(uid)
        } else {
            initActionBar("设备详情",true)
            "加载失败，请重试！".showToast()
        }
    }

//    val handler : Handler = object :Handler(Looper.getMainLooper()){
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            when(msg.what) {
//                0 -> {
//                    val gson = Gson()
//                    val strings : Array<String> = gson.fromJson(msg.obj.toString(),Array<String>::class.java)
//                    val equipment = gson.fromJson<Equipment>(strings[0],Equipment::class.java)
//                    initActionBar(equipment.name,true)
//                    binding.etEquipmentShowId.text =  equipment.uid.toString()
//                    binding.etEquipmentShowModel.text = equipment.model
//                    val projectList : MutableList<String> = gson.fromJson(strings[1], object : TypeToken<MutableList<String>>(){}.type)
//                    val list : MutableList<KeyPairBoolData> = mutableListOf()
//                    if (equipment.project.equals("null")) {
//                        val k : KeyPairBoolData = KeyPairBoolData()
//                        k.id = 1
//                        k.name = projectList[0]
//                        k.isSelected = true
//                        list.add(k)
//                        old = "null"
//                        for (index  in 1 until projectList.size) {
//                            val h : KeyPairBoolData = KeyPairBoolData()
//                            h.id = (index+1).toLong()
//                            h.name = projectList[index]
//                            h.isSelected = false
//                            list.add(h)
//                        }
//                    } else {
//                        old = equipment.project
//                        for (index in 0 until projectList.size) {
//                            val h : KeyPairBoolData = KeyPairBoolData()
//                            h.id = (index+1).toLong()
//                            h.name = projectList[index]
//                            h.isSelected = projectList[index] == old
//                            list.add(h)
//                        }
//                    }
//                    binding.spEquipmentShow.setSearchHint("搜索项目")
//                    binding.spEquipmentShow.setItems(list, object : SingleSpinnerListener{
//                        override fun onItemsSelected(selectedItem: KeyPairBoolData) {
//                            binding.tvButton.visibility = View.VISIBLE
//                            binding.tvButton.singleClick {
//                                thread {
//                                    val str = selectedItem.name
//                                    val strUrl = Constant.WEB_ADDRESS + "/equipment"
//                                    val map: MutableMap<String, String> = java.util.HashMap()
//                                    map["is"] = "3"
//                                    map["old"] = old
//                                    map["equipment"] = equipment.name
//                                    map["uid"] = equipment.uid.toString()
//                                    map["name"] = str
//                                    val res = WebUtil.loginsend(strUrl, map)
//                                    val message = Message()
//                                    message.what = 1
//                                    message.obj = res
//                                    handleMessage(message)
//                                }
//                            }
//                        }
//
//                        override fun onClear() {
//                        }
//                    })
//                }
//                1 -> {
//                    if (msg.obj.toString() == "true") {
//                        "修改成功！".showToast()
//                    } else {
//                        "修改失败！".showToast()
//                    }
//                }
//            }
//        }
//    }

    fun initData(uid : String) {
        val map : HashMap<String,Any> = HashMap()
        map["is"] = "2"
        map["uid"] = uid
        loading()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = ApiRetrofit().getApiService().equipmentshow(map)
                if (res.code == 200) {
                    val gson = Gson()
                    val strings = res.data
                    val equipment = gson.fromJson<Equipment>(strings[0],Equipment::class.java)
                    initActionBar(equipment.name,true)
                    binding.etEquipmentShowId.text =  equipment.uid.toString()
                    binding.etEquipmentShowModel.text = equipment.model
                    val projectList : MutableList<String> = gson.fromJson(strings[1], object : TypeToken<MutableList<String>>(){}.type)
                    val list : MutableList<KeyPairBoolData> = mutableListOf()
                    if (equipment.project.equals("null")) {
                        val k : KeyPairBoolData = KeyPairBoolData()
                        k.id = 1
                        k.name = projectList[0]
                        k.isSelected = true
                        list.add(k)
                        old = "null"
                        for (index  in 1 until projectList.size) {
                            val h : KeyPairBoolData = KeyPairBoolData()
                            h.id = (index+1).toLong()
                            h.name = projectList[index]
                            h.isSelected = false
                            list.add(h)
                        }
                    } else {
                        old = equipment.project
                        for (index in 0 until projectList.size) {
                            val h : KeyPairBoolData = KeyPairBoolData()
                            h.id = (index+1).toLong()
                            h.name = projectList[index]
                            h.isSelected = projectList[index] == old
                            list.add(h)
                        }
                    }
                    binding.spEquipmentShow.setSearchHint("搜索项目")
                    binding.spEquipmentShow.setItems(list,object : SingleSpinnerListener{
                        override fun onItemsSelected(selectedItem: KeyPairBoolData) {
                            loading()
                            binding.tvButton.visibility = View.VISIBLE
                            binding.tvButton.singleClick {
                                val str = selectedItem.name
                                val map : HashMap<String,Any> = HashMap()
                                map["is"] = "3"
                                map["old"] = old
                                map["equipment"] = equipment.name
                                map["uid"] = equipment.uid.toString()
                                map["name"] = str
                                val api = ApiRetrofit().getApiService()
                                CoroutineScope(Dispatchers.Main).launch {
                                    try {
                                        val res = api.equipmentupdate(map)
                                        if (res.code == 200) {
                                            "修改成功！".showToast()
                                        } else {
                                            "修改失败！".showToast()
                                        }
                                        dismiss()
                                    } catch (e : Exception) {
                                        dismiss()
                                        "修改失败！".showToast()
                                    }
                                }
                            }
                        }

                        override fun onClear() {
                        }

                    })
                }
                dismiss()
            } catch (e : Exception) {
                e.printStackTrace()
                dismiss()
            }
        }
//        thread {
//            val strUrl = Constant.WEB_ADDRESS + "/equipment"
//            val map : MutableMap<String,String> = HashMap()
//            map["is"] = "2"
//            map["uid"] = uid
//            val res = WebUtil.loginsend(strUrl,map)
//            val message = Message()
//            message.what = 0
//            message.obj = res
//            handler.sendMessage(message)
//        }
    }
}