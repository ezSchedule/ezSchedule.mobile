package com.ezschedule.network.data.data

import com.google.gson.annotations.SerializedName

data class TenantData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String?
)