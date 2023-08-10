package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.TenantLoginData
import com.ezschedule.network.domain.response.TenantLoginResponse

fun TenantLoginData.toResponse() = TenantLoginResponse(
    id = id,
    email = email,
    name = name,
    cpf = cpf,
    password = password,
    residentsBlock = residentsBlock,
    apartmentNumber = apartmentNumber,
    phoneNumber = phoneNumber,
    image = image ?: "",
    isAuthenticated = isAuthenticated,
    isAdmin = isAdmin == 1,
    tokenJWT = tokenJWT,
    idCondominium = idCondominium
)