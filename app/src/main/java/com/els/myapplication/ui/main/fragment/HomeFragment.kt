package com.els.myapplication.ui.main.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.els.myapplication.App
import com.els.myapplication.Constant
import com.els.myapplication.Constant.TAG
import com.els.myapplication.R
import com.els.myapplication.adapter.MeAdapter
import com.els.myapplication.adapter.NoticeAdapter
import com.els.myapplication.bean.Data
import com.els.myapplication.bean.MeItem
import com.els.myapplication.bean.Notice
import com.els.myapplication.databinding.FragmentHomeBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.ui.qccode.QrCodeActivity
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    var loading: LoadingPopupView? = null
    private lateinit var binding: FragmentHomeBinding
    private val list = mutableListOf<MeItem>()
    private val mylist = mutableListOf<MeItem>()
    private val meAdapter = MeAdapter()
    private val noticeAdapter = NoticeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list.clear()
        mylist.clear()
        list.add(MeItem(1,"请假",R.drawable.ic_leave))
        list.add(MeItem(2,"项目",R.drawable.ic_project))
        list.add(MeItem(3,"财务申请",R.drawable.ic_financial))
        list.add(MeItem(4,"修改密码",R.drawable.ic_register))
        list.add(MeItem(5,"反馈建议",R.drawable.ic_feedback))
        list.add(MeItem(6,"请假管理",R.drawable.ic_leave_manage))
        list.add(MeItem(7,"通知",R.drawable.ic_inform))
        list.add(MeItem(8,"人事",R.drawable.ic_personnel))
        list.add(MeItem(9,"项目管理",R.drawable.ic_project))
        list.add(MeItem(10,"设备管理",R.drawable.ic_equipment))
        list.add(MeItem(11,"财务审批",R.drawable.ic_finanial_manage))
        list.add(MeItem(12,"公告",R.drawable.ic_notice))
        list.add(MeItem(13,"添加设备",R.drawable.ic_equipment_add))
        list.add(MeItem(0,"权限管理",R.drawable.ic_root))
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        initData()
        return binding.root
    }

//    val handler : Handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            when(msg.what) {
//                0 -> {
//                    val gson = Gson()
//                    try {
//                        val array : Array<String> = gson.fromJson(msg.obj.toString(),Array<String>::class.java)
//                        val mutableList = gson.fromJson<List<Int>>(array[0],object : TypeToken<List<Int>>(){}.type)
//                        mutableList.forEach {
//                            mylist.add(list[it-1])
//                        }
//                        mylist.add(MeItem(1,"请假",R.drawable.ic_leave))
//                        mylist.add(MeItem(2,"项目",R.drawable.ic_project))
//                        mylist.add(MeItem(3,"财务申请",R.drawable.ic_financial))
//                        mylist.add(MeItem(4,"修改密码",R.drawable.ic_register))
//                        mylist.add(MeItem(5,"反馈建议",R.drawable.ic_feedback))
//                        if(MainActivity.user?.root == 0) {
//                            meAdapter.setNewInstance(list)
//                        } else {
//                            meAdapter.setNewInstance(mylist)
//                        }
//                        binding.tvHomeTitle.text = array[1]
//                    } catch (e : java.lang.Exception) {
//                        e.printStackTrace()
//                    }
//                    dismiss()
//                }
//            }
//        }
//    }

    private fun initData() {
        loading()
        val mutableList = mutableListOf<MeItem>()
        val title = "测试内容！测试内容！测试内容！测试内容！测试内容！测试内容！测试内容！测试内容！"
        when (Calendar.getInstance()[Calendar.HOUR_OF_DAY]) {
            in 6..12 -> {
                binding.tvName.text = MainActivity.user?.username + "，上午好！"
            }
            in 12..18 -> {
                binding.tvName.text = MainActivity.user?.username + "，下午好！"
            }
            else -> {
                binding.tvName.text = MainActivity.user?.username + "，晚上好！"
            }
        }
        binding.tvTime.text = SimpleDateFormat("MM月dd日 EEEE").format(System.currentTimeMillis())
        binding.tvHomeTitle.text = title
        binding.tvHomeTitle.isSelected = true
        binding.rvHoemMe.layoutManager = GridLayoutManager(activity, 4)
        binding.rvHoemMe.adapter = meAdapter
        binding.rvHomeNotice.layoutManager =LinearLayoutManager(activity)
        binding.rvHomeNotice.adapter = noticeAdapter
        val data = mutableListOf(Notice.Data(0,"","",1," ",1,1," ","测试内容1测试内容1测试内容1"," "),Notice.Data(0,"","",1," ",1,1," ","测试内容2测试内容2测试内容2"," "),Notice.Data(0,"","",1," ",1,1," ","测试内容3测试内容3测试内容3"," "))
        Log.e(TAG, "initData: " + data.size )
        noticeAdapter.setNewInstance(data)
        val map : HashMap<String,Any> = HashMap()
        map["is"] = "0"
        map["name"] = MainActivity.user!!.username
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.meItem(map)
                if (res.code == 200) {
                    val gson = Gson()
                    val mutableList = gson.fromJson<List<Int>>(res.data[0],object : TypeToken<List<Int>>(){}.type)
                    mutableList.forEach {
                        mylist.add(list[it-1])
                    }
                    mylist.add(MeItem(1,"请假",R.drawable.ic_leave))
                    mylist.add(MeItem(2,"项目",R.drawable.ic_project))
                    mylist.add(MeItem(3,"财务申请",R.drawable.ic_financial))
                    mylist.add(MeItem(4,"修改密码",R.drawable.ic_register))
                    mylist.add(MeItem(5,"反馈建议",R.drawable.ic_feedback))
                    if(MainActivity.user?.root == 0) {
                        meAdapter.setNewInstance(list)
                    } else {
                        meAdapter.setNewInstance(mylist)
                    }
                    binding.tvHomeTitle.text = res.data[1]
                }
                dismiss()
            } catch (e : Exception) {
                e.printStackTrace()
                dismiss()
            }

        }
//        thread {
//            val strUrl = Constant.WEB_ADDRESS + "/me"
//            val map : HashMap<String,String> = HashMap()
//            map["is"] = "0"
//            map["name"] = MainActivity.user!!.username
//            val res = WebUtil.loginsend(strUrl,map)
//            val message = Message()
//            message.what = 0
//            message.obj = res
//            Log.e(Constant.TAG,res)
//            handler.sendMessage(message)
//        }
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val menuItem = menu.findItem(R.id.item_scan)
        menuItem.setOnMenuItemClickListener {
            IntentIntegrator(requireActivity())
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    .setCameraId(0)
                    .setPrompt("11111")
                    .setBeepEnabled(true)
                    .setCaptureActivity(QrCodeActivity::class.java)
                    .initiateScan()
            false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun loading() {
        loading = XPopup.Builder(requireContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(true)
                .hasStatusBarShadow(false)
                .hasShadowBg(false).asLoading()
        if (!loading?.isShow!!) {
            loading?.show()
        }
    }

    fun dismiss() {
        try {
            if (loading!!.isShow)
                loading?.dismiss()
            loading = null
        } catch (e: Exception) {

        }

    }

    fun test() {
        val map : HashMap<String,Any> = HashMap()
        map["is"] = "0"
        map["mess"] = "lsx"
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.test(map)
                res.data.showToast()
            } catch (e : Exception) {
                e.printStackTrace()
                e.toString().showToast()
            }
        }
    }

}