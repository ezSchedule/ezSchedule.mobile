package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.ServiceUserPresentation

data class ServiceResponse(
    val serviceId: Int,
    val serviceName: String,
    val tenantId: Int,
    val tenantName: String,
    val tenantImage: String?,
    val tenantPhoneNumber: String,
    val tenantBlock: String,
    val tenantApartmentNumber: String
) {
    fun toPresentation() = ServicePresentation(
        image = tenantImage,
        name = serviceName,
        userName = tenantName,
        userNumber = tenantPhoneNumber
    )

    fun toUserPresentation() = ServiceUserPresentation(
        image = tenantImage,
        name = serviceName,
        userName = tenantName,
        phone = tenantPhoneNumber,
        block = tenantBlock,
        apartmentNumber = tenantApartmentNumber
    )
}