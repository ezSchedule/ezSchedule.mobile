package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.TenantData
import com.ezschedule.network.domain.data.TenantServiceData
import com.ezschedule.network.domain.data.TenantSettingsData
import com.ezschedule.network.domain.presentation.TenantServicePresentation
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

fun TenantSettingsData.toResponse() = TenantResponse(
    id = id,
    name = name,
    email = email,
    cpf = cpf,
    password = "",
    residentsBlock = residentsBlock,
    apartmentNumber = apartmentNumber,
    phoneNumber = phoneNumber,
    image = image ?: "",
    isAuthenticated = isAuthenticated,
    isAdmin = isAdmin == 1,
    tokenJWT = "",
    idCondominium = condominium.id
)

fun TenantServiceData.toResponse() = TenantResponse(
    id = id,
    name = name,
    email = email,
    cpf = cpf,
    password = "",
    residentsBlock = residentsBlock,
    apartmentNumber = apartmentNumber,
    phoneNumber = phoneNumber,
    image = image ?: "",
    isAuthenticated = isAuthenticated,
    isAdmin = isAdmin == 1,
    tokenJWT = "",
    idCondominium = 0
)

fun TenantResponse.toServicePresentation() = TenantServicePresentation(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
    image = image
)