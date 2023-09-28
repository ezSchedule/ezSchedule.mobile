package com.ezschedule.network.domain.data.calendar

import com.google.gson.annotations.SerializedName

data class ScheduleSalonData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("saloonName")
    val name: String,
    @SerializedName("saloonPrice")
    val price: Double,
    @SerializedName("saloonBlock")
    val block: String
)