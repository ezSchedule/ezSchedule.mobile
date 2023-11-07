package com.ezschedule.network.domain.data

data class ServiceRequest(
    private val serviceName: String,
    private val tenant: ServiceTenant
)

data class ServiceTenant(
    private val id: Int
)