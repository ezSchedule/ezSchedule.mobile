package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.ServiceData
import com.ezschedule.network.domain.response.ServiceResponse
import com.ezschedule.network.domain.response.ServicesResponse

fun List<ServiceData>.toResponse() = ServicesResponse(
    results = this.map {
        it.toResponse()
    }
)

fun ServiceData.toResponse() = ServiceResponse(
    serviceId = id,
    serviceName = serviceName,
    tenantId = tenant.id,
    tenantName = tenant.name,
    tenantImage = tenant.image ?: "",
    tenantPhoneNumber = tenant.phoneNumber,
    tenantBlock = tenant.residentsBlock,
    tenantApartmentNumber = tenant.apartmentNumber.toString()
)


