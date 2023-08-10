package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.LoginPresentation

data class LoginResponse(
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
    fun toLoginPresentation(): LoginPresentation = LoginPresentation(
        id = id,
        name = name,
        email = email,
        tokenJWT = tokenJWT,
        idCondominium = idCondominium
    )
}