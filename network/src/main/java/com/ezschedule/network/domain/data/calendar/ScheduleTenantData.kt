package com.ezschedule.network.domain.data.calendar

import com.google.gson.annotations.SerializedName

data class ScheduleTenantData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("cpf")
    val cpf: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("residentsBlock")
    val residentsBlock: String,
    @SerializedName("apartmentNumber")
    val apartmentNumber: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("authenticated")
    val isAuthenticated: Boolean,
    @SerializedName("isAdmin")
    val isAdmin: Int,
    @SerializedName("token")
    val tokenJWT: String,
    @SerializedName("idCondominium")
    val idCondominium: Int
)