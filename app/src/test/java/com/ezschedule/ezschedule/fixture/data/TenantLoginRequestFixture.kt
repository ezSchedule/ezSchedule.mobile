package com.ezschedule.ezschedule.fixture.data

import com.ezschedule.network.domain.data.TenantLoginRequest

class TenantLoginRequestFixture(
    val email: String,
    val password: String
) {
    companion object {
        fun getTenantLoginRequestComplete(
            withEmail: String = "endryl@gmail.com",
            withPassword: String = "123"
        ): TenantLoginRequestFixture {
            return TenantLoginRequestFixture(
                email = withEmail,
                password = withPassword
            )
        }
    }

    fun build() = TenantLoginRequest(
        email = email,
        password = password
    )
}