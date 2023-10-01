package com.ezschedule.network.domain.presentation

data class TenantSettingsPresentation(
    val fullName: String,
    val firstName: String,
    val surname: String,
    val cpf: String,
    val apartmentNumber: Int,
    val residentsBlock: String,
    val phoneNumber: String,
    val email: String,
    val image: String?
)