package com.els.myapplication.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.CleanUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.els.myapplication.Constant
import com.els.myapplication.R
import com.els.myapplication.bean.User
import com.els.myapplication.databinding.FragmentMeBinding
import com.els.myapplication.retrofit.ApiRetrofit
import com.els.myapplication.showToast
import com.els.myapplication.ui.login.LoginNewActivity
import com.els.myapplication.ui.main.activity.MainActivity
import com.els.myapplication.ui.other.AboutMeActivity
import com.els.myapplication.ui.other.ChanagePassActivity
import com.els.myapplication.utils.GlideEngine
import com.els.myapplication.utils.ShpUtil
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*


class MeFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentMeBinding
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = MainActivity.user!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init() {
        binding.clChangePass.setOnClickListener(this)
        binding.clAboutMe.setOnClickListener(this)
        binding.clExit.setOnClickListener(this)
        binding.clClean.setOnClickListener(this)
        binding.imgHeader.setOnClickListener(this)
        binding.switchInform.setOnClickListener(this)
        binding.textViewUserName.text = user.username
        binding.textViewUserId.text = user.id.toString()
        binding.textViewUserRegular.let {
            when(user.root) {
                0 -> it.text = "管理员用户"
                1 -> it.text = "正式员工"
                2 -> it.text = "临时员工"
            }
        }
        val shpUtil = ShpUtil(requireContext(), "image")
        val path : String = shpUtil.load("path")
        if (path.isNotEmpty()) {
            Glide.with(requireContext()).load(path)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.imgHeader)
        }
        val ischeck : String = shpUtil.load("is")
        if (ischeck.isNotEmpty()) {
            if (ischeck == "ok") {
                binding.switchInform.isChecked = true
            }
            if (ischeck == "no") {
                binding.switchInform.isChecked = false
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.clAboutMe -> {
                startActivity(Intent(requireContext(),AboutMeActivity::class.java))
            }
            R.id.clChangePass -> {
                startActivity(Intent(requireContext(),ChanagePassActivity::class.java))
            }
            R.id.clExit -> {
                val shpUtil = ShpUtil(requireContext(), "login")
                shpUtil.save("login", "false")
                startActivity(
                        Intent(
                                requireContext(),
                                LoginNewActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                activity?.finish()
            }
            R.id.clClean -> {
                var title = "清除缓存"
                var message = "确定要清除缓存吗?"
                var isNeedClear = false
                if (binding.tvCleanSize.visibility == View.GONE || binding.tvCleanSize.text == "0.0B") {
                    title = ""
                    message = "已经很干净啦，不用清理了"
                } else {
                    isNeedClear = true
                }
                AlertDialog.Builder(requireContext()).setTitle(title).setMessage(message)
                        .setPositiveButton(
                                "确定"
                        ) { p0, _ ->
                            if (isNeedClear) {
                                CleanUtils.cleanInternalCache()
                                CleanUtils.cleanInternalFiles()
                                binding.tvCleanSize.visibility = View.GONE
                            }
                            p0.dismiss()
                        }.setNegativeButton("取消") { p0, _ -> p0.dismiss() }.show()
            }
            R.id.img_header -> {
                EasyPhotos.createAlbum(requireActivity(), true, false, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.els.myapplication.fileprovider")
                        .start(object : SelectCallback() {
                            override fun onResult(photos: ArrayList<Photo>?, isOriginal: Boolean) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    try {
                                        val file = File(photos?.get(0)?.path)
                                        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                                        val api = ApiRetrofit().getApiService()
                                        val res = api.upload(body)
                                        if (res.code == 200) {
                                            val shpUtil = ShpUtil(requireContext(), "image")
                                            shpUtil.clear()
                                            val path: String = res.data
                                            shpUtil.save("path", Constant.WEB_ADDRESS + "/upload/" + path)
                                            Glide.with(requireContext()).load(Constant.WEB_ADDRESS + "/upload/" + path)
                                                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                                                    .into(binding.imgHeader)
                                        } else {
                                            "上传失败，请重试！".showToast()
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        "上传失败，请重试！".showToast()
                                    }
                                }
                            }

                            override fun onCancel() {
                                "上传失败，请重试！".showToast()
                            }
                        })
            }
            R.id.switchInform -> {
                val shpUtil = ShpUtil(requireContext(), "image")
                if (binding.switchInform.isChecked) {
                    shpUtil.save("is","ok")
                } else {
                    shpUtil.save("is","no")
                }
            }
        }
    }


}