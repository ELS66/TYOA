package com.els.myapplication.bean

import com.google.gson.annotations.SerializedName

data class Notice(
        @SerializedName("code")
        val code: Int,
        @SerializedName("count")
        val count: Int,
        @SerializedName("data")
        val `data`: MutableList<Data>,
        @SerializedName("message")
        val message: String
) {
    data class Data(
            @SerializedName("contains_file")
            val containsFile: Int,
            @SerializedName("file_name")
            val fileName: String?,
            @SerializedName("file_url")
            val fileUrl: String?,
            @SerializedName("id")
            val id: Int,
            @SerializedName("LX")
            val lX: String,
            @SerializedName("readcount")
            val readcount: Int,
            @SerializedName("sfyxpl")
            val sfyxpl: Int,
            @SerializedName("time")
            val time: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("zz")
            val zz: String
    )
}