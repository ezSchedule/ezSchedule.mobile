package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.ServicePresentation

data class ServiceResponse(
    val serviceId: Int,
    val serviceName: String,
    val tenantId: Int,
    val tenantName: String,
    val tenantImage: String?,
    val tenantPhoneNumber: String
) {
    fun toPresentation() = ServicePresentation(
        image = tenantImage,
        name = serviceName,
        userName = tenantName,
        userNumber = tenantPhoneNumber

    )
}