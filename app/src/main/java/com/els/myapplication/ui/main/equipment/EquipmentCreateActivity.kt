package com.els.myapplication.ui.main.equipment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.els.myapplication.base.BaseActivity
import com.els.myapplication.databinding.ActivityEquipmentCreateBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.ui.main.fragment.EquipmentCreateFragment
import com.els.myapplication.utils.ImageSaveUtil
import com.els.myapplication.utils.QcCodeUtil
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.collections.set

class EquipmentCreateActivity : BaseActivity() {

    private lateinit var binding: ActivityEquipmentCreateBinding
    private lateinit var bitmap : Bitmap
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipmentCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar("添加设备",true)
        initView()
    }

    fun initView() {
        binding.tvEquipmentAdd.singleClick {
            if (binding.etEquipmentCreateId.text.trim().isEmpty()) {
                "请输入设备UID".showToast()
                return@singleClick
            }
            if (binding.etEquipmentCreateName.text.trim().isEmpty()) {
                "请输入设备名称".showToast()
                return@singleClick
            }
            if (binding.etEquipmentCreateModel.text.trim().isEmpty()) {
                "请输入设备种类".showToast()
                return@singleClick
            }
            loading()
            val map : HashMap<String,Any> = HashMap()
            map["is"] = "0"
            map["id"] = binding.etEquipmentCreateId.text.toString().trim()
            map["name"] = binding.etEquipmentCreateName.text.toString().trim()
            map["model"] = binding.etEquipmentCreateModel.text.toString().trim()
            val api = ApiRetrofit().getApiService()
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res = api.equipmentcreate(map)
                    if (res.code == 200) {
                        bitmap = QcCodeUtil.createImage(binding.etEquipmentCreateId.text.toString().trim(),binding.etEquipmentCreateName.text.toString().trim())
                        bitmap.let {
                            binding.tvEquipmentAdd.visibility = View.GONE
                            binding.ivEquipementCreate.visibility = View.VISIBLE
                            binding.ivEquipementCreate.setImageBitmap(it)
                            binding.tvEquipmentCeateToast.visibility = View.VISIBLE
                            binding.ivEquipementCreate.setOnClickListener {
                                showListDialog(bitmap)
                            }
                        }
                    } else {
                        "创建失败，请重试！".showToast()
                    }
                    dismiss()
                } catch (e : Exception) {
                    e.printStackTrace()
                    dismiss()
                }
            }
        }
    }

    fun showListDialog(bitmap: Bitmap?) {
        val items = arrayOf("保存至手机", "分享")
        val dialog = AlertDialog.Builder(this)
        dialog.setItems(items) { dialogInterface, i ->
            if (i == 0) {
                saveImg()
            } else if (i == 1) {
                bitmap?.let { share(this, it, binding.etEquipmentCreateId.text.toString().trim() + "_" + binding.etEquipmentCreateName.text.toString().trim()) }
            }
        }
        dialog.show()
    }

    private fun saveImg() {
        XXPermissions.with(this)
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: List<String>, all: Boolean) {
                        if (all) {
                            //Toast.makeText(requireContext(),"获取存储权限成功",Toast.LENGTH_SHORT).show();
                            uri = ImageSaveUtil.saveAlbum(this@EquipmentCreateActivity, bitmap, Bitmap.CompressFormat.JPEG, 100, true, binding.etEquipmentCreateId.text.toString().trim(), binding.etEquipmentCreateName.text.toString().trim())!!
                            "保存成功！".showToast()
                        }
                    }

                    override fun onDenied(permissions: List<String>, never: Boolean) {
                        if (never) {
                            "被永久拒绝授权，请手动授予存储权限".showToast()
                            XXPermissions.startPermissionActivity(this@EquipmentCreateActivity, permissions)
                        } else {
                            "获取存储权限失败".showToast()
                        }
                    }
                })
    }

    private fun share(context: Context, bitmap: Bitmap, name: String) {
        val storePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val filename = "$name.jpg"
        val file = File(appDir, filename)
        try {
            val fos = FileOutputStream(file)
            val isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos)
            fos.flush()
            fos.close()
            if (isSuccess) {
                val intent = Intent(Intent.ACTION_SEND)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val contentUri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
                    intent.putExtra(Intent.EXTRA_STREAM, contentUri)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } else {
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                }
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "分享")
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(chooser)
                }
            } else {
                Toast.makeText(context, "分享失败，请重试。", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}