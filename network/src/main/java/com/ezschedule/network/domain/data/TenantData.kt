package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName

data class TenantData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("cpf")
    val cpf: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("residentsBlock")
    val residentsBlock: String,
    @SerializedName("apartmentNumber")
    val apartmentNumber: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isAuthenticated")
    val isAuthenticated: Boolean,
    @SerializedName("isAdmin")
    val isAdmin: Int,
    @SerializedName("token")
    val tokenJWT: String,
    @SerializedName("idCondominium")
    val idCondominium: Int
)