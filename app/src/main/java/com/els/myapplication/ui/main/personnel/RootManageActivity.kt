package com.els.myapplication.ui.main.personnel

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.adapter.RootManageAdapter
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.MeItem
import com.els.myapplication.databinding.ActivityRootManageBinding
import com.els.myapplication.showToast
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.concurrent.thread

class RootManageActivity : BaseActivity() {

    private lateinit var binding: ActivityRootManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("权限管理",true)
        init()
    }

     val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> {
                    val gson = Gson()
                    val mutableList = gson.fromJson<List<String>>(msg.obj.toString(),object : TypeToken<List<String>>(){}.type)
                    val array = mutableList.toTypedArray()
                    val num = msg.arg1
                    AlertDialog.Builder(this@RootManageActivity)
                            .setTitle("请选择人员")
                            .setItems(array, DialogInterface.OnClickListener { dialogInterface, i ->
                                thread {
                                    val strUrl = Constant.WEB_ADDRESS + "/root"
                                    val map : HashMap<String,String> = HashMap()
                                    map["is"] = "2"
                                    map["name"] = array[i]
                                    map["root"] = "i$num"
                                    val res = WebUtil.loginsend(strUrl,map)
                                }
                            }).show()
                    dismiss()
                }
                1 -> {
                    val gson = Gson()
                    val mutableList = gson.fromJson<List<String>>(msg.obj.toString(),object : TypeToken<List<String>>(){}.type)
                    val array = mutableList.toTypedArray()
                    val num = msg.arg1
                    AlertDialog.Builder(this@RootManageActivity)
                            .setTitle("请选择人员")
                            .setItems(array, DialogInterface.OnClickListener { dialogInterface, i ->
                                thread {
                                    val strUrl = Constant.WEB_ADDRESS + "/root"
                                    val map : HashMap<String,String> = HashMap()
                                    map["is"] = "3"
                                    map["name"] = array[i]
                                    map["root"] = "i$num"
                                    val res = WebUtil.loginsend(strUrl,map)
                                }
                            }).show()
                    dismiss()
                }
            }
        }
    }

    fun init() {
        val mutableList = mutableListOf<MeItem>()
        mutableList.add(MeItem(6,"请假管理", R.drawable.ic_leave_manage))
        mutableList.add(MeItem(7,"通知", R.drawable.ic_inform))
        mutableList.add(MeItem(8,"人事", R.drawable.ic_personnel))
        mutableList.add(MeItem(9,"项目", R.drawable.ic_project))
        mutableList.add(MeItem(10,"设备管理", R.drawable.ic_equipment))
        mutableList.add(MeItem(11,"财务审批", R.drawable.ic_finanial_manage))
        mutableList.add(MeItem(12,"公告", R.drawable.ic_notice))
        mutableList.add(MeItem(13,"添加设备",R.drawable.ic_equipment_add))
        binding.rvRoot.layoutManager = LinearLayoutManager(this)
        val rootManageAdapter = RootManageAdapter()
        binding.rvRoot.adapter = rootManageAdapter
        rootManageAdapter.setNewInstance(mutableList)
        rootManageAdapter.addChildClickViewIds(R.id.iv_add)
        rootManageAdapter.addChildClickViewIds(R.id.iv_del)
        rootManageAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id) {
                R.id.iv_add -> {
                   thread {
                       loading()
                       thread {
                           val strUrl = Constant.WEB_ADDRESS + "/root"
                           val map : HashMap<String,String> = HashMap()
                           map["is"] = "0"
                           map["root"] = "i${position+6}"
                           val res = WebUtil.loginsend(strUrl,map)
                           val message = Message()
                           message.what = 0
                           message.obj = res
                           message.arg1 = position + 6
                           handler.sendMessage(message)
                       }
                   }
                }
                R.id.iv_del -> {
                    loading()
                    thread {
                        val strUrl = Constant.WEB_ADDRESS + "/root"
                        val map : HashMap<String,String> = HashMap()
                        map["is"] = "1"
                        map["root"] = "i${position+6}"
                        val res = WebUtil.loginsend(strUrl,map)
                        val message = Message()
                        message.what = 1
                        message.obj = res
                        message.arg1 = position + 6
                        handler.sendMessage(message)
                    }
                }
            }
        }


    }

}