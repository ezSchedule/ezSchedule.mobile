package com.ezschedule.network.domain.data

data class PixRequest(
    val name: String,
    val cpf: String,
    val value: String,
    val paymentDescription: String,
    val condominiumId: Int
)
