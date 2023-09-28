package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.TenantData
import com.ezschedule.network.domain.response.TenantResponse

fun TenantData.toResponse() = TenantResponse(
    id = id,
    email = email ?: "",
    name = name ?: "",
    cpf = cpf ?: "",
    password = password ?: "",
    residentsBlock = residentsBlock ?: "",
    apartmentNumber = apartmentNumber ?: 0,
    phoneNumber = phoneNumber ?: "",
    image = image ?: "",
    isAuthenticated = isAuthenticated ?: false,
    isAdmin = isAdmin == 1,
    tokenJWT = tokenJWT ?: "",
    idCondominium = idCondominium ?: 0
)