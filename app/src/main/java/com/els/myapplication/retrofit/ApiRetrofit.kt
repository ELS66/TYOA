package com.els.myapplication.retrofit

class ApiRetrofit : BaseRetrofit(){

    fun getApiService() : ApiService {
        return getInstance().createService(ApiService::class.java)
    }

    companion object {
        val instance by lazy (LazyThreadSafetyMode.NONE) {
            ApiRetrofit
        }
    }

}