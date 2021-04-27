package com.els.myapplication.ui.main.personnel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.adapter.MyAdapter
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.bean.MyItem
import com.els.myapplication.databinding.ActivityPersonnelBinding
import com.els.myapplication.showToast
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import kotlin.concurrent.thread

class PersonnelActivity : BaseActivity() {

    val list = mutableListOf<MyItem>()
    lateinit var binding: ActivityPersonnelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonnelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("人事",true)
        list.add(MyItem("添加员工",R.drawable.item_set,R.drawable.item_to))
        list.add(MyItem("删除员工",R.drawable.item_set,R.drawable.item_to))
        list.add(MyItem("管理权限",R.drawable.item_set,R.drawable.item_to))
        list.add(MyItem("管理员账号",R.drawable.item_set,R.drawable.item_to))
        initData()
    }

    val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                0 -> {
                    dialogcreate(msg.obj.toString(),"删除员工", "您确定删除吗？", "2", 1)
                }
                1 -> {
                    toastshow(msg.obj.toString(), "删除成功！", "删除失败，请重试！")
                }
                2 -> {
                    dialogcreate(msg.obj.toString(), "临时员工转正式员工", "您确定修改吗？", "5", 4)
                }
                3 -> {
                    dialogcreate(msg.obj.toString(), "正式员工转临时员工", "您确定修改吗？", "6", 5)
                }
                4 -> {
                    toastshow(msg.obj.toString(), "修改成功！", "修改失败，请重试！")
                }
                5 -> {
                    toastshow(msg.obj.toString(), "修改成功！", "修改失败，请重试！")
                }
                6 -> {
                    dialogcreate(msg.obj.toString(), "添加管理员账号", "您确定添加吗？", "8", 8)
                }
                7 -> {
                    dialogcreate(msg.obj.toString(), "删除管理员账号", "您确定删除吗？", "5", 9)
                }
                8 -> {
                    toastshow(msg.obj.toString(), "添加成功!", "添加失败，请重试!")
                }
                9 -> {
                    toastshow(msg.obj.toString(), "删除成功！", "删除失败，请重试！")
                }
            }
        }
    }

    fun initData() {
        val myAdapter = MyAdapter()
        binding.recyclerViewPersonnel.adapter = myAdapter
        binding.recyclerViewPersonnel.layoutManager = LinearLayoutManager(this)
        myAdapter.submitList(list)
        myAdapter.setOnMyClickListener {
            when(it) {
                0 -> {
                    thread {
                        val strUrl = Constant.WEB_ADDRESS + "/personnel"
                        val map = HashMap<String,String>()
                        map["is"] = "1"
                        val res = WebUtil.loginsend(strUrl,map)
                        val message = Message()
                        message.what = 0;
                        message.obj = res
                        handler.sendMessage(message)
                    }
                }
                1 -> {
                    val items = arrayOf("临时员工转正式员工", "正式员工转临时员工")
                    val alertDialog = AlertDialog.Builder(this)
                            .setTitle("管理权限")
                            .setItems(items) { dialogInterface, i ->
                                if (i == 0) {
                                    Thread(Runnable {
                                        val strUrl = Constant.WEB_ADDRESS + "/personnel"
                                        val map: MutableMap<String, String> = java.util.HashMap()
                                        map["is"] = "3"
                                        val res = WebUtil.loginsend(strUrl, map)
                                        val message = Message()
                                        message.what = 2
                                        message.obj = res
                                        handler.sendMessage(message)
                                    }).start()
                                } else {
                                    Thread(Runnable {
                                        val strUrl = Constant.WEB_ADDRESS + "/personnel"
                                        val map: MutableMap<String, String> = java.util.HashMap()
                                        map["is"] = "4"
                                        val res = WebUtil.loginsend(strUrl, map)
                                        val message = Message()
                                        message.what = 3
                                        message.obj = res
                                        handler.sendMessage(message)
                                    }).start()
                                }
                            }
                            .setNegativeButton("取消") { dialogInterface, i -> }
                            .show()
                }
                2 -> {
                    val editText = EditText(this)
                    val alertDialog = AlertDialog.Builder(this)
                            .setTitle("请输入管理员密码")
                            .setView(editText)
                            .setPositiveButton("确定") { dialogInterface, i ->
                                if (editText.text.toString().trim() == Constant.ROOT_PASSWORD) {
                                    val items = arrayOf("添加管理员账号", "删除管理员账号")
                                    val alertDialog = AlertDialog.Builder(this)
                                            .setTitle("管理员账号")
                                            .setItems(items) { dialogInterface, i ->
                                                if (i == 0) {
                                                    Thread(Runnable {
                                                        val strUrl = Constant.WEB_ADDRESS + "/personnel"
                                                        val map: MutableMap<String, String> = HashMap()
                                                        map["is"] = "1"
                                                        val res = WebUtil.loginsend(strUrl, map)
                                                        val message = Message()
                                                        message.what = 6
                                                        message.obj = res
                                                        handler.sendMessage(message)
                                                    }).start()
                                                } else {
                                                    Thread(Runnable {
                                                        val strUrl = Constant.WEB_ADDRESS + "/personnel"
                                                        val map: MutableMap<String, String> = HashMap()
                                                        map["is"] = "7"
                                                        val res = WebUtil.loginsend(strUrl, map)
                                                        Log.d(Constant.TAG, res)
                                                        val message = Message()
                                                        message.what = 7
                                                        message.obj = res
                                                        handler.sendMessage(message)
                                                    }).start()
                                                }
                                            }
                                            .setNegativeButton("取消") { dialogInterface, i -> }
                                            .show()
                                } else {
                                    dialogInterface.cancel()
                                    "密码错误，请重新输入！".showToast()
                                }
                            }
                            .setNegativeButton("取消") { dialogInterface, i -> }
                            .show()
                }
            }
        }
    }

    private fun dialogcreate(str: String, title: String, message: String, `is`: String, what: Int) {
        val gson = Gson()
        val strings = gson.fromJson(str, Array<String>::class.java)
        val nums = intArrayOf(0)
        val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setSingleChoiceItems(strings, 0) { dialogInterface, i -> nums[0] = i }
                .setNegativeButton("取消") { dialogInterface, i -> }
                .setPositiveButton("确定") { dialogInterface, i ->
                    val alertDialog1 = AlertDialog.Builder(this)
                            .setTitle("警告")
                            .setMessage(message)
                            .setNegativeButton("取消") { dialogInterface, i -> }
                            .setPositiveButton("确定") { dialogInterface, i ->
                                Thread(Runnable {
                                    val strUrl = Constant.WEB_ADDRESS + "/personnel"
                                    val map: MutableMap<String, String> = java.util.HashMap()
                                    map["is"] = `is`
                                    map["name"] = strings[nums[0]]
                                    val res = WebUtil.loginsend(strUrl, map)
                                    val message = Message()
                                    message.what = what
                                    message.obj = res
                                    handler.sendMessage(message)
                                }).start()
                            }
                            .show()
                }
                .show()
    }

    private fun toastshow(string: String, ok: String, no: String) {
        val res = string.toInt()
        if (res == 1) {
            ok.showToast()
        } else {
            no.showToast()
        }
    }


}