package com.ezschedule.network.domain.presentation

data class ServiceUserPresentation(
    val image: String?,
    val name: String,
    val userName: String,
    val phone: String,
    val block: String,
    val apartmentNumber: String
)