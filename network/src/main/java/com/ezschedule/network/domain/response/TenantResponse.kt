package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.data.CondominiumRequest
import com.ezschedule.network.domain.data.TenantScheduleRequest
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.presentation.TenantSettingsPresentation

data class TenantResponse(
    val id: Int,
    val name: String,
    val email: String,
    val cpf: String,
    val password: String,
    val residentsBlock: String,
    val apartmentNumber: Int,
    val phoneNumber: String,
    val image: String,
    val isAuthenticated: Boolean,
    val isAdmin: Boolean,
    val tokenJWT: String,
    val idCondominium: Int
) {
    fun toTenantPresentation(): TenantPresentation = TenantPresentation(
        id = id,
        name = name,
        email = email,
        image = image,
        tokenJWT = tokenJWT,
        isAdmin = isAdmin,
        idCondominium = idCondominium
    )

    fun toTenantSettingsPresentation(): TenantSettingsPresentation = TenantSettingsPresentation(
        fullName = name,
        firstName = name.split("\\s".toRegex())[0],
        surname = name.split("\\s".toRegex()).drop(1).joinToString(),
        cpf = cpf,
        apartmentNumber = apartmentNumber,
        residentsBlock = residentsBlock,
        phoneNumber = phoneNumber,
        email = email,
        image = image
    )

    fun toTenantScheduleRequest() = TenantScheduleRequest(
        id = id,
        name = name,
        residentsBlock = residentsBlock,
        apartmentNumber = apartmentNumber,
        phoneNumber = phoneNumber,
        condominium = CondominiumRequest(idCondominium)
    )
}