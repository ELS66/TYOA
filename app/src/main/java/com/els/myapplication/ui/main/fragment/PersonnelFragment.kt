package com.els.myapplication.ui.main.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.adapter.MyAdapter
import com.els.myapplication.bean.MyItem
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import java.util.*

class PersonnelFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var list: MutableList<MyItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list.add(MyItem("添加员工", R.drawable.item_set, R.drawable.item_to))
        list.add(MyItem("删除员工", R.drawable.item_set, R.drawable.item_to))
        list.add(MyItem("管理权限", R.drawable.item_set, R.drawable.item_to))
        list.add(MyItem("管理员账号", R.drawable.item_set, R.drawable.item_to))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_personnel, container, false)
        init(root)
        return root
    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView_personnel)
        val myAdapter = MyAdapter()
//        recyclerView.setAdapter(myAdapter)
//        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))
        myAdapter.submitList(list)
        myAdapter.setOnMyClickListener { `is` ->
            when (`is`) {
                0 -> {
                    Thread(Runnable {
                        val strUrl = Constant.WEB_ADDRESS + "personnel"
                        val map: MutableMap<String, String> = HashMap()
                        map["is"] = "1"
                        val res = WebUtil.loginsend(strUrl, map)
                        val message = Message()
                        message.what = 0
                        message.obj = res
                        handler.sendMessage(message)
                    }).start()
                }
                1 -> {
                    val items = arrayOf("临时员工转正式员工", "正式员工转临时员工")
                    val alertDialog = AlertDialog.Builder(requireContext())
                            .setTitle("管理权限")
                            .setItems(items) { dialogInterface, i ->
                                if (i == 0) {
                                    Thread(Runnable {
                                        val strUrl = Constant.WEB_ADDRESS + "personnel"
                                        val map: MutableMap<String, String> = HashMap()
                                        map["is"] = "3"
                                        val res = WebUtil.loginsend(strUrl, map)
                                        val message = Message()
                                        message.what = 2
                                        message.obj = res
                                        handler.sendMessage(message)
                                    }).start()
                                } else {
                                    Thread(Runnable {
                                        val strUrl = Constant.WEB_ADDRESS + "personnel"
                                        val map: MutableMap<String, String> = HashMap()
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
                    val editText = EditText(requireContext())
                    val alertDialog = AlertDialog.Builder(requireContext())
                            .setTitle("请输入管理员密码")
                            .setView(editText)
                            .setPositiveButton("确定") { dialogInterface, i ->
                                if (editText.text.toString() == Constant.ROOT_PASSWORD) {
                                    val items = arrayOf("添加管理员账号", "删除管理员账号")
                                    val alertDialog = AlertDialog.Builder(requireContext())
                                            .setTitle("管理员账号")
                                            .setItems(items) { dialogInterface, i ->
                                                if (i == 0) {
                                                    Thread(Runnable {
                                                        val strUrl = Constant.WEB_ADDRESS + "personnel"
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
                                                        val strUrl = Constant.WEB_ADDRESS + "personnel"
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
                                    Toast.makeText(requireContext(), "密码错误，请重新输入！", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("取消") { dialogInterface, i -> }
                            .show()
                }
            }
        }
    }

    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    dialogcreate(msg.obj.toString(), "删除员工", "您确定删除吗？", "2", 1)
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

    private fun dialogcreate(str: String, title: String, message: String, `is`: String, what: Int) {
        val gson = Gson()
        val strings = gson.fromJson(str, Array<String>::class.java)
        val nums = intArrayOf(0)
        val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setSingleChoiceItems(strings, 0) { dialogInterface, i -> nums[0] = i }
                .setNegativeButton("取消") { dialogInterface, i -> }
                .setPositiveButton("确定") { dialogInterface, i ->
                    val alertDialog1 = AlertDialog.Builder(requireContext())
                            .setTitle("警告")
                            .setMessage(message)
                            .setNegativeButton("取消") { dialogInterface, i -> }
                            .setPositiveButton("确定") { dialogInterface, i ->
                                Thread(Runnable {
                                    val strUrl = Constant.WEB_ADDRESS + "personnel"
                                    val map: MutableMap<String, String> = HashMap()
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
            Toast.makeText(requireContext(), ok, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), no, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val LOCAL_BROADCAST = "com.xfhy.casualweather.LOCAL_BROADCAST"
    }
}