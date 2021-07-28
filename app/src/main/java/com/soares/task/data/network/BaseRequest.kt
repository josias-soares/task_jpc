package com.soares.task.data.network

import com.google.gson.annotations.SerializedName
import com.soares.task.BuildConfig
import java.io.Serializable

open class BaseRequest : Serializable {
    @SerializedName("Id")
    var version: String = BuildConfig.VERSION_NAME
}