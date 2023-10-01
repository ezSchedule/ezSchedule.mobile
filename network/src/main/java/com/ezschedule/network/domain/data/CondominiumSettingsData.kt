package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName

data class CondominiumSettingsData(
    @SerializedName("amountTenants")
    val residents: Int,
    @SerializedName("amountApartments")
    val apartments: Int,
    @SerializedName("amountSaloons")
    val saloons: Int
)
