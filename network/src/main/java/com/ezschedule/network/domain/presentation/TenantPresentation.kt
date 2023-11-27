package com.ezschedule.network.domain.presentation

data class TenantPresentation(
    val id: Int,
    val email: String,
    val name: String,
    val image: String,
    val cpf: String,
    val isAdmin: Boolean,
    val tokenJWT: String,
    val idCondominium: Int
)