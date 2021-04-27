package com.els.myapplication.ui.main.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.utils.WebUtil
import java.util.*

class PersonnelAddFragment : Fragment() {
    var editText: EditText? = null
    var spinner: Spinner? = null
    var button: Button? = null
    var items = arrayOf("管理人员", "正式员工", "临时员工")
    var r = -1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_personnel_add, container, false)
        init(root)
        button!!.setOnClickListener {
            if (editText!!.text.toString().trim { it <= ' ' }.length == 0) {
                Toast.makeText(requireContext(), "请输入员工姓名", Toast.LENGTH_SHORT).show()
            } else {
                Thread(Runnable {
                    val strUrl = Constant.WEB_ADDRESS + "/personnel"
                    val map: MutableMap<String, String> = HashMap()
                    map["is"] = "0"
                    map["name"] = editText!!.text.toString().trim { it <= ' ' }
                    map["root"] = r.toString()
                    val res = WebUtil.loginsend(strUrl, map)
                    val message = Message()
                    message.what = 0
                    message.obj = res
                    handler.sendMessage(message)
                }).start()
            }
        }
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                r = i
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                r = -1
            }
        }
        return root
    }

    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    val res = msg.obj.toString().toInt()
                    if (res == -1 || res == 0) {
                        Toast.makeText(requireContext(), "添加失败，请重试！", Toast.LENGTH_SHORT).show()
                    } else {


                    }
                }
            }
        }
    }

    private fun init(view: View) {
        editText = view.findViewById(R.id.editText_personnel_add_name)
        button = view.findViewById(R.id.button_personnel_add)
        spinner = view.findViewById(R.id.spinner_personnel_add_root)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        //spinner.setAdapter(adapter)
    }
}