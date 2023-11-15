package com.ezschedule.network.domain.data

import okhttp3.RequestBody

data class TenantUpdateRequest(
    val name: String,
    val cpf: String,
    val apartmentNumber: Int,
    val residentsBlock: String,
    val phoneNumber: String,
    val email: String,
    val image: Map<String, RequestBody>?
)