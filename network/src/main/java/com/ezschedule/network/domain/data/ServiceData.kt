package com.ezschedule.network.domain.data

data class ServiceData(
    val id: Int,
    val serviceName: String,
    val tenant: TenantServiceData
)