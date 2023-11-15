package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName

data class TenantSettingsData(
    val name: String,
    val email: String,
    val cpf: String,
    val residentsBlock: String,
    val apartmentNumber: Int,
    val phoneNumber: String,
    val isAdmin: Int,
    @SerializedName("nameBlobImage")
    val image: String?,
    @SerializedName("authenticated")
    val isAuthenticated: Boolean
)