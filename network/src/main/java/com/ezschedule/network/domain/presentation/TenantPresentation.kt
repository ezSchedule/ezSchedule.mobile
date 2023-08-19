package com.ezschedule.network.domain.presentation

data class TenantPresentation(
    val id: Int,
    val email: String,
    val name: String,
    val image: String,
    val tokenJWT: String,
    val idCondominium: Int
)