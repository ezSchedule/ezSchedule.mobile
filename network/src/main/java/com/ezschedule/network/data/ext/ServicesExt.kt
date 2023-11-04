package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.ServiceData
import com.ezschedule.network.domain.data.TenantServiceData
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import com.ezschedule.network.domain.response.ServiceResponse
import com.ezschedule.network.domain.response.TenantResponse

fun List<ServiceData>.toResponse() = this.map { it.toResponse() }

fun ServiceData.toResponse() = ServiceResponse(
    serviceId = id,
    serviceName = serviceName,
    tenantId = tenant.id,
    tenantName = tenant.name,
    tenantImage = tenant.image ?: "",
    tenantPhoneNumber = tenant.phoneNumber

)


