package com.ezschedule.network.domain.data.calendar

import com.google.gson.annotations.SerializedName

data class ScheduleData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nameEvent")
    val name: String,
    @SerializedName("typeEvent")
    val type: String?,
    @SerializedName("dateEvent")
    val date: String,
    @SerializedName("isCanceled")
    val isCanceled: Int,
    @SerializedName("totalNumberGuests")
    val numberGuests: Int,
    @SerializedName("saloon")
    val salonData: ScheduleSalonData?,
    @SerializedName("tenant")
    val tenantData: ScheduleTenantData
)