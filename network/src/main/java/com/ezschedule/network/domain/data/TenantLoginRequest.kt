package com.ezschedule.network.domain.data

data class TenantLoginRequest(
    val email: String,
    val password: String
)