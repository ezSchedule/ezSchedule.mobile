package com.ezschedule.network.domain.presentation

data class SettingsPresentation(
    val name: String,
    val email: String,
    val cpf: String,
    val residentsBlock: String,
    val apartmentNumber: Int,
    val phoneNumber: String,
    val image: String?,
)