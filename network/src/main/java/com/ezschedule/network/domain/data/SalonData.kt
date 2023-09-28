package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName

data class SalonData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("saloonName")
    val name: String,
    @SerializedName("saloonPrice")
    val price: Double,
    @SerializedName("saloonBlock")
    val block: String
)