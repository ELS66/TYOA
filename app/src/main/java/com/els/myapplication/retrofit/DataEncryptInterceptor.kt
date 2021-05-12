package com.els.myapplication.retrofit

import android.text.TextUtils
import android.util.Log
import com.els.myapplication.utils.RsaEncryptUtil
import okhttp3.*
import okio.Buffer


class DataEncryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
//    override fun intercept(chain: Interceptor.Chain): Response {
//        //请求
//        //请求
//        var request: Request = chain.request()
//        val mediaType: MediaType? = MediaType.parse("text/plain; charset=utf-8")
//        //随机生成AES秘钥
//        //随机生成AES秘钥
//        val aesKey: String = AESUtil.generateKey()
//        try {
//            //获取未加密数据
//            val oldRequestBody: RequestBody? = request.body()
//            val requestBuffer = Buffer()
//            oldRequestBody?.writeTo(requestBuffer)
//            val oldBodyStr: String = requestBuffer.readUtf8()
//            requestBuffer.close()
//
//            //未加密数据用AES秘钥加密
//            val newBodyStr: String? = AESUtil.encrypt(aesKey,oldBodyStr)
//            //AES秘钥用服务端RSA公钥加密
//            val key: String = RsaEncryptUtil.encryptByPublicKey(aesKey)
//            //构成新的request 并通过请求头发送加密后的AES秘钥
//            val headers: Headers = request.headers()
//            val newBody = RequestBody.create(mediaType, newBodyStr)
//            //构造新的request
//            request = request.newBuilder()
//                    .headers(headers)
//                    .addHeader("Device-Key", key)
//                    .method(request.method(), newBody)
//                    .build()
//        } catch (e: Exception) {
//        }
//        //响应
//        //响应
//        var response = chain.proceed(request)
//        if (response.code() == 200) {
//            try {
//                //获取加密的响应数据
//                val oldResponseBody = response.body()
//                val oldResponseBodyStr = oldResponseBody!!.string()
//                //加密的响应数据用AES秘钥解密
//                var newResponseBodyStr = ""
//                if (!TextUtils.isEmpty(oldResponseBodyStr)) {
//                    newResponseBodyStr = AESUtil.encrypt(oldResponseBodyStr, aesKey)!!
//                }
//                oldResponseBody.close()
//                //构造新的response
//                val newResponseBody = ResponseBody.create(mediaType, newResponseBodyStr)
//                response = response.newBuilder().body(newResponseBody).build()
//            } catch (e: Exception) {
//                Log.d("RetrofitLog", "e" + e.message)
//            } finally {
//                response.close()
//            }
//        }
//        //返回
//        //返回
//        return response
//    }

}