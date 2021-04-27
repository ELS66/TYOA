package com.els.myapplication.ui.main.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.SingleSpinnerListener
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.bean.Equipment
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class EquipmentShowFragment : Fragment() {
    var textView_name: TextView? = null
    var textView_id: TextView? = null
    var textView_model: TextView? = null
    var singleSpinnerSearch: SingleSpinnerSearch? = null
    var button: Button? = null
    var projectList: List<String>? = null
    var old: String? = null
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        val root = inflater.inflate(R.layout.fragment_equipment_show, container, false)
//        init(root)
//        MainActivity.tv_title!!.text = "设备"
//        return root
//    }

//    var handler: Handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            if (msg.what == 0) {
//                val gson = Gson()
//                val strings = gson.fromJson(msg.obj.toString(), Array<String>::class.java)
//                val equipment = gson.fromJson(strings[0], Equipment::class.java)
//                textView_name!!.text = equipment.name
//                textView_id!!.text = equipment.uid.toString()
//                textView_model!!.text = equipment.model
//                projectList = gson.fromJson<List<String>>(strings[1], object : TypeToken<List<String?>?>() {}.type)
//                val list: MutableList<KeyPairBoolData> = ArrayList()
//                if (equipment.project == "null") {
//                    val k = KeyPairBoolData()
//                    k.id = 1
//                    k.name = projectList.get(0)
//                    k.isSelected = true
//                    list.add(k)
//                    old = "null"
//                    for (i in 1 until projectList.size) {
//                        val h = KeyPairBoolData()
//                        h.id = i + 1.toLong()
//                        h.name = projectList.get(i)
//                        h.isSelected = false
//                        list.add(h)
//                    }
//                } else {
//                    old = equipment.project
//                    for (i in projectList.indices) {
//                        val h = KeyPairBoolData()
//                        h.id = i + 1.toLong()
//                        h.name = projectList.get(i)
//                        if (projectList.get(i) == old) {
//                            h.isSelected = true
//                        } else {
//                            h.isSelected = false
//                        }
//                        list.add(h)
//                    }
//                }
//                singleSpinnerSearch!!.setSearchHint("搜索项目")
//                singleSpinnerSearch!!.setItems(list, object : SingleSpinnerListener {
//                    override fun onItemsSelected(selectedItem: KeyPairBoolData) {
//                        button!!.visibility = View.VISIBLE
//                        button!!.setOnClickListener {
//                            Thread(Runnable {
//                                val str = selectedItem.name
//                                val strUrl = Constant.WEB_ADDRESS + "equipment"
//                                val map: MutableMap<String, String?> = HashMap()
//                                map["is"] = "3"
//                                map["old"] = old
//                                map["equipment"] = equipment.name
//                                map["uid"] = equipment.uid.toString()
//                                map["name"] = str
//                                val res = WebUtil.loginsend(strUrl, map)
//                            }).start()
//                        }
//                    }
//
//                    override fun onClear() {}
//                })
//            }
//        }
//    }

//    private fun init(view: View) {
//        textView_name = view.findViewById(R.id.textView_equipment_show_name)
//        textView_id = view.findViewById(R.id.textView_equipment_show_id)
//        textView_model = view.findViewById(R.id.textView_equipment_show_model)
//        singleSpinnerSearch = view.findViewById(R.id.singlespinnerseach_equipment_show)
//        button = view.findViewById(R.id.button_equipment_show)
//        val bundle = this.arguments
//        if (bundle != null) {
//            val uid = bundle.getString("uid")
//            Thread(Runnable {
//                val strUrl = Constant.WEB_ADDRESS + "equipment"
//                val map: MutableMap<String, String?> = HashMap()
//                map["is"] = "2"
//                map["uid"] = uid
//                val res = WebUtil.loginsend(strUrl, map)
//                val message = Message()
//                message.what = 0
//                message.obj = res
//                handler.sendMessage(message)
//            }).start()
//        }
//    }
}