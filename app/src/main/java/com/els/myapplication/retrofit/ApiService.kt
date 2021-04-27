package com.els.myapplication.retrofit

import com.els.myapplication.base.BaseResult
import com.els.myapplication.bean.Equipment
import com.els.myapplication.bean.Leave
import com.els.myapplication.bean.Project
import com.els.myapplication.bean.User
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("tyoa/test")
    suspend fun test (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

    @FormUrlEncoded
    @POST("tyoa/login")
    suspend fun login (@FieldMap map: HashMap<String,Any>) : BaseResult<User>

    @FormUrlEncoded
    @POST("tyoa/leave")
    suspend fun leave (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

    @FormUrlEncoded
    @POST("tyoa/leave")
    suspend fun leavemanage (@FieldMap map: HashMap<String,Any>) : BaseResult<MutableList<Leave>>

    @FormUrlEncoded
    @POST("tyoa/leave")
    suspend fun leavemanagetrue (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

    @FormUrlEncoded
    @POST("tyoa/leave")
    suspend fun leavemanagefalse (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

    @FormUrlEncoded
    @POST("tyoa/me")
    suspend fun meItem (@FieldMap map: HashMap<String,Any>) : BaseResult<Array<String>>

    @FormUrlEncoded
    @POST("tyoa/project")
    suspend fun getproject (@FieldMap map: HashMap<String,Any>) : BaseResult<MutableList<Project>>

    @FormUrlEncoded
    @POST("tyoa/project")
    suspend fun getprojectmanage (@FieldMap map: HashMap<String,Any>) : BaseResult<MutableList<MutableList<String>>>

    @FormUrlEncoded
    @POST("tyoa/project")
    suspend fun postproject (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

    @FormUrlEncoded
    @POST("tyoa/project")
    suspend fun projectshow (@FieldMap map: HashMap<String,Any>) : BaseResult<Array<String>>

    @FormUrlEncoded
    @POST("tyoa/equipment")
    suspend fun equipmentcreate (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

    @FormUrlEncoded
    @POST("tyoa/equipment")
    suspend fun equipmentmanage (@FieldMap map: HashMap<String,Any>) : BaseResult<List<Equipment>>

    @FormUrlEncoded
    @POST("tyoa/equipment")
    suspend fun equipmentshow (@FieldMap map: HashMap<String,Any>) : BaseResult<Array<String>>

    @FormUrlEncoded
    @POST("tyoa/equipment")
    suspend fun equipmentupdate (@FieldMap map: HashMap<String,Any>) : BaseResult<String>

}