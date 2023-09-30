package com.ezschedule.network.domain.data

import com.google.gson.annotations.SerializedName

data class ScheduleData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("nameEvent")
    val name: String,
    @SerializedName("typeEvent")
    val type: String?,
    @SerializedName("dateEvent")
    val date: String,
    @SerializedName("isCanceled")
    val isCanceled: Int,
    @SerializedName("totalNumberGuests")
    val numberGuests: Int?,
    @SerializedName("saloon")
    val salonData: SalonData?,
    @SerializedName("tenant")
    val tenantData: TenantData
) {
    companion object {
        fun cancelDay(reason: String, date: String, idTenant: Int) = ScheduleData(
            id = null,
            name = reason,
            type = null,
            date = date,
            isCanceled = 1,
            numberGuests = null,
            salonData = null,
            tenantData = TenantData(
                id = idTenant,
                name = null,
                email = null,
                cpf = null,
                password = null,
                residentsBlock = null,
                apartmentNumber = null,
                phoneNumber = null,
                image = null,
                isAuthenticated = null,
                isAdmin = null,
                tokenJWT = null,
                idCondominium = null
            )
        )
    }
}