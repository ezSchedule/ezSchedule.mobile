package com.ezschedule.ezschedule.fixture.response

import com.ezschedule.network.domain.response.LoginResponse

class LoginResponseFixture(
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
    companion object {
        fun getLoginResponseComplete(
            withId: Int = 1,
            withName: String = "endryl fiorotti",
            withEmail: String = "endryl@gmail.com",
            withCpf: String = "324.324.432-76",
            withPassword: String = "123",
            withResidentsBlock: String = "1A",
            withApartmentNumber: Int = 12,
            withPhoneNumber: String = "(11) 94251-8747",
            withImage: String = "image",
            withIsAuthenticated: Boolean = true,
            withIsAdmin: Boolean = true,
            withTokenJWT: String = "token",
            withIdCondominium: Int = 1
        ): LoginResponseFixture {
            return LoginResponseFixture(
                id = withId,
                name = withName,
                email = withEmail,
                cpf = withCpf,
                password = withPassword,
                residentsBlock = withResidentsBlock,
                apartmentNumber = withApartmentNumber,
                phoneNumber = withPhoneNumber,
                image = withImage,
                isAuthenticated = withIsAuthenticated,
                isAdmin = withIsAdmin,
                tokenJWT = withTokenJWT,
                idCondominium = withIdCondominium
            )
        }
    }

    fun build() = LoginResponse(
        id = id,
        name = name,
        email = email,
        cpf = cpf,
        password = password,
        residentsBlock = residentsBlock,
        apartmentNumber = apartmentNumber,
        phoneNumber = phoneNumber,
        image = image,
        isAuthenticated = isAuthenticated,
        isAdmin = isAdmin,
        tokenJWT = tokenJWT,
        idCondominium = idCondominium
    )
}