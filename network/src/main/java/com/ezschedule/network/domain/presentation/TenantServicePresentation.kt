package com.ezschedule.network.domain.presentation

data class TenantServicePresentation(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val image: String?
)