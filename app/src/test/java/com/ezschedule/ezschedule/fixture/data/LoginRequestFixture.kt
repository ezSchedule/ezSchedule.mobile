package com.ezschedule.ezschedule.fixture.data

import com.ezschedule.network.domain.data.LoginRequest

class LoginRequestFixture(
    val email: String,
    val password: String
) {
    companion object {
        fun getLoginRequestComplete(
            withEmail: String = "endryl@gmail.com",
            withPassword: String = "123"
        ): LoginRequestFixture {
            return LoginRequestFixture(
                email = withEmail,
                password = withPassword
            )
        }
    }

    fun build() = LoginRequest(
        email = email,
        password = password
    )
}