package com.els.myapplication.ui.main.project

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.els.myapplication.Constant
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.databinding.ActivityMapAddBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.utils.CitySelectPicker
import com.els.myapplication.utils.WebUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.common.StringUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class MapAddActivity : BaseActivity(),GeocodeSearch.OnGeocodeSearchListener {

    private lateinit var binding : ActivityMapAddBinding
    private val employeelist: MutableList<String> = ArrayList()
    private val managementlist: MutableList<String> = ArrayList()
    private var options1 = 0
    private var options2 = 0
    private var options3 = 0
    //private val booleans: BooleanArray
    private val equipmentList: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("添加项目",true)
        loading()
        initData()

    }

    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
//                0 -> {
//                    val res = msg.obj.toString()
//                    val gson = Gson()
//                    val lists = gson.fromJson<List<List<String>>>(res, object : TypeToken<List<List<String>>>() {}.type)
//                    val listemployee = lists[0]
//                    val listmanage = lists[1]
//                    val listequipment = lists[2]
////                    val listemployee = listOf("lll","kkk")
////                    val listmanage = listOf("sffs","asfaf")
////                    val listequipment = listOf("sfas","gasg")
//                    val list0: MutableList<KeyPairBoolData> = ArrayList()
//                    listemployee.forEachIndexed { index, s ->
//                        val h = KeyPairBoolData()
//                        h.id = index.toLong()
//                        h.name = s
//                        h.isSelected = false
//                        list0.add(h)
//                    }
//                    binding.spMapEmployee.let {
//                        it.isSearchEnabled = true
//                        it.setSearchHint("请输入人员姓名")
//                        it.setEmptyTitle("没有找到，请重新输入")
//                        it.setClearText("清除选择")
//                        it.setItems(list0) { /*for (int i = 0; i < items.size(); i++) {
//                                if (items.get(i).isSelected()) {
//                                    employeelist.add(items.get(i).getName());
//                                }
//                            }*/
//                        }
//                    }
//                    val list1: MutableList<KeyPairBoolData> = ArrayList()
//                    listmanage.forEachIndexed { index, s ->
//                        val h = KeyPairBoolData()
//                        h.id = index.toLong()
//                        h.name = s
//                        h.isSelected = false
//                        list1.add(h)
//                    }
//                    binding.spMapManager.let {
//                        it.isSearchEnabled = true
//                        it.setSearchHint("请输入人员姓名")
//                        it.setEmptyTitle("没有找到，请重新输入")
//                        it.setClearText("清除选择")
//                        it.setItems(list1){
//
//                        }
//                    }
//                    val list2: MutableList<KeyPairBoolData> = ArrayList()
//                    listequipment.forEachIndexed { index, s ->
//                        val h = KeyPairBoolData()
//                        h.id = index.toLong()
//                        h.name = s
//                        h.isSelected = false
//                        list2.add(h)
//                    }
//                    binding.spMapEquipment.let {
//                        it.isSearchEnabled = true
////                        it.hintText = "请输入设备名称"
//                        it.setEmptyTitle("没有找到，请重新输入")
//                        it.setClearText("清除选择")
//                        it.setItems(list2) { }
//                    }
//
//                }
//                1 -> {
//                    dismiss()
//                    finish()
//                    "添加成功".showToast()
//                }
                2 -> {
                    binding.tvMaoAddAddress.text = msg.obj.toString()
                }
            }
        }
    }

    private fun initData() {
        val map : HashMap<String,Any> = HashMap()
        map["is"] = "0"
        val api = ApiRetrofit().getApiService()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = api.getprojectmanage(map)
                val gson = Gson()
                if (res.code == 200) {
                    val lists = res.data
                    val listemployee = lists[0]
                    val listmanage = lists[1]
                    val listequipment = lists[2]
                    val list0: MutableList<KeyPairBoolData> = ArrayList()
                    listemployee.forEachIndexed { index, s ->
                        val h = KeyPairBoolData()
                        h.id = index.toLong()
                        h.name = s
                        h.isSelected = false
                        list0.add(h)
                    }
                    binding.spMapEmployee.let {
                        it.isSearchEnabled = true
                        it.setSearchHint("请输入人员姓名")
                        it.setEmptyTitle("没有找到，请重新输入")
                        it.setClearText("清除选择")
                        it.setItems(list0) {}
                    }
                    val list1: MutableList<KeyPairBoolData> = ArrayList()
                    listmanage.forEachIndexed { index, s ->
                        val h = KeyPairBoolData()
                        h.id = index.toLong()
                        h.name = s
                        h.isSelected = false
                        list1.add(h)
                    }
                    binding.spMapManager.let {
                        it.isSearchEnabled = true
                        it.setSearchHint("请输入人员姓名")
                        it.setEmptyTitle("没有找到，请重新输入")
                        it.setClearText("清除选择")
                        it.setItems(list1){

                        }
                    }
                    val list2: MutableList<KeyPairBoolData> = ArrayList()
                    listequipment.forEachIndexed { index, s ->
                        val h = KeyPairBoolData()
                        h.id = index.toLong()
                        h.name = s
                        h.isSelected = false
                        list2.add(h)
                    }
                    binding.spMapEquipment.let {
                        it.isSearchEnabled = true
//                        it.hintText = "请输入设备名称"
                        it.setEmptyTitle("没有找到，请重新输入")
                        it.setClearText("清除选择")
                        it.setItems(list2) { }
                    }
                }
                dismiss()
            } catch (e : Exception) {
                e.printStackTrace()
                dismiss()
            }
        }
//        Thread(Runnable {
//            val strUrl = Constant.WEB_ADDRESS + "/project"
//            val map: MutableMap<String, String> = HashMap()
//            map["is"] = "0"
//            val res = WebUtil.loginsend(strUrl, map)
//            val message = Message()
//            message.what = 0
//            message.obj = res
//            handler.sendMessage(message)
//        }).start()
        binding.tvMaoAddAddress.singleClick {
            val picker = CitySelectPicker()
            picker.setListener { position1: Int, position2: Int, position3: Int ->
                options1 = position1
                options2 = position2
                options3 = position3
            }
            picker.getInstance(this@MapAddActivity, handler, options1, options2, options3)
        }
        binding.buttonMapAdd.singleClick {
            if (binding.etMapName.text.toString() == "") {
                "请输入项目名称".showToast()
                return@singleClick
            }
            if (binding.tvMaoAddAddress.text.toString() == "") {
                "请选择项目地点".showToast()
                return@singleClick
            }
            if (binding.spMapManager.selectedItems.size == 0) {
                "请选择管理人员".showToast()
                return@singleClick
            }
            if (binding.spMapEmployee.selectedItems.size == 0) {
                "请选择工作人员".showToast()
                return@singleClick
            }
            loading()
            val geocoderSearch  = GeocodeSearch(this)
            geocoderSearch.setOnGeocodeSearchListener(this)
            val geocodeQuery = GeocodeQuery(binding.tvMaoAddAddress.text.toString(),"")
            geocoderSearch.getFromLocationNameAsyn(geocodeQuery)
        }
    } /*private class ProjectEquAdapter extends RecyclerView.Adapter<ProjectEquAdapter.InnerHolder> {

        private List<Equipment> equipmentList;

        public ProjectEquAdapter (List<Equipment> equipmentList) {
            this.equipmentList = equipmentList;
        }

        @NonNull
        @Override
        public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.item_project_equipment,parent,false);
            final  InnerHolder holder = new InnerHolder(itemView);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    booleans[holder.getAdapterPosition()] = b;
                    Log.d(Constant.TAG, String.valueOf(holder.getAdapterPosition()));
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
            holder.setData(equipmentList.get(position));
        }

        @Override
        public int getItemCount() {
            if (equipmentList.size() != 0) {
                return employeelist.size();
            }
            return 0;
        }


        public class InnerHolder extends RecyclerView.ViewHolder {

            TextView textView_name,textView_project;
            CheckBox checkBox;

            public InnerHolder(@NonNull View itemView) {
                super(itemView);
                textView_name = itemView.findViewById(R.id.textView_project_equipment_name);
                textView_project = itemView.findViewById(R.id.textView_project_equipment_project);
                checkBox = itemView.findViewById(R.id.checkBox_project_equipment_is);
            }

            public void setData(Equipment equipment) {
                textView_name.setText(equipment.getName());
                if (equipment.getProject() == "null") {
                    textView_project.setText("闲置");
                } else {
                    textView_project.setText(equipment.getProject());
                }
            }
        }
    }*/

    override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onGeocodeSearched(p0: GeocodeResult, p1: Int) {
        if (p1 == 1000) {
            val list1 = binding.spMapEmployee.selectedItems
            val list2 = binding.spMapManager.selectedItems
            val list3 = binding.spMapEquipment.selectedItems
            for (i in list1.indices) {
                if (list1[i].isSelected) {
                    employeelist.add(list1[i].name)
                }
            }
            for (i in list2.indices) {
                if (list2[i].isSelected) {
                    managementlist.add(list2[i].name)
                }
            }
            for (i in list3.indices) {
                if (list3[i].isSelected) {
                    equipmentList.add(list3[i].name.substringBefore(" "))
                }
            }
            val gson = Gson()
            val employee = gson.toJson(employeelist)
            val management = gson.toJson(managementlist)
            val equipment: String
            equipment = if (equipmentList.size == 0) {
                "null"
            } else {
                gson.toJson(equipmentList)
            }
            val map : HashMap<String,Any> = HashMap()
            map["is"] = "1"
            map["employee"] = employee
            map["management"] = management
            map["name"] = binding.etMapName.text.toString()
            map["address"] = binding.tvMaoAddAddress.text.toString()
            map["equipment"] = equipment
            map["longitude"] = p0.geocodeAddressList[0].latLonPoint.longitude.toString()
            map["latitude"] = p0.geocodeAddressList[0].latLonPoint.latitude.toString()
            val api = ApiRetrofit().getApiService()
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res = api.postproject(map)
                    dismiss()
                    if (res.code == 200) {
                        val buidler = AlertDialog.Builder(this@MapAddActivity)
                        buidler.setTitle("添加成功")
                                .setMessage("项目编号为${res.data}")
                                .setNegativeButton("确定") { _, _ ->
                                    finish()
                                }.show()
                    } else {
                        "提交失败，请重试！".showToast()
                    }
                    dismiss()
                } catch (e : Exception){
                    e.printStackTrace()
                    dismiss()
                    "提交失败，请重试！".showToast()
                    throw e
                }
            }
//            Thread(Runnable {
//                val list1 = binding.spMapEmployee.selectedItems
//                val list2 = binding.spMapManager.selectedItems
//                val list3 = binding.spMapEquipment.selectedItems
//                for (i in list1.indices) {
//                    if (list1[i].isSelected) {
//                        employeelist.add(list1[i].name)
//                    }
//                }
//                for (i in list2.indices) {
//                    if (list2[i].isSelected) {
//                        managementlist.add(list2[i].name)
//                    }
//                }
//                for (i in list3.indices) {
//                    if (list3[i].isSelected) {
//                        equipmentList.add(list3[i].name.substringBefore(" "))
//                    }
//                }
//                val strUrl = Constant.WEB_ADDRESS + "/project"
//                val gson = Gson()
//                val employee = gson.toJson(employeelist)
//                val management = gson.toJson(managementlist)
//                val equipment: String
//                equipment = if (equipmentList.size == 0) {
//                    "null"
//                } else {
//                    gson.toJson(equipmentList)
//                }
//                val map: MutableMap<String, String> = HashMap()
//                map["is"] = "1"
//                map["employee"] = employee
//                map["management"] = management
//                map["name"] = binding.etMapName.text.toString()
//                map["address"] = binding.tvMaoAddAddress.text.toString()
//                map["equipment"] = equipment
//                map["longitude"] = p0.geocodeAddressList[0].latLonPoint.longitude.toString()
//                map["latitude"] = p0.geocodeAddressList[0].latLonPoint.latitude.toString()
//                Log.e("ELSELS", "onGeocodeSearched: " + p0.geocodeAddressList.size )
//                val res = WebUtil.loginsend(strUrl, map)
//                val message = Message()
//                message.what = 1
//                message.obj = res
//                handler.sendMessage(message)
//            }).start()
        } else {
            Log.e("ELSELS", "onGeocodeSearched: $p1" )
            dismiss()
            "地址查询失败！ 错误码 $p1".showToast()
        }
    }
}