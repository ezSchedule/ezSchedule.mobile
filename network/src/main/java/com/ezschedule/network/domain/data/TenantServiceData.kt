package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName

data class TenantServiceData(
    val id: Int,
    val email: String,
    val cpf: String,
    val name: String,
    val residentsBlock: String,
    val apartmentNumber: Int,
    val phoneNumber: String,
    val isAdmin: Int,
    @SerializedName("nameBlobImage")
    val image: String?,
    @SerializedName("authenticated")
    val isAuthenticated: Boolean,
    val services: List<ServiceData>
)