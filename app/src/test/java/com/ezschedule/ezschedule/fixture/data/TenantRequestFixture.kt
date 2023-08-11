package com.ezschedule.ezschedule.fixture.data

import com.ezschedule.network.domain.data.TenantRequest

class TenantRequestFixture(
    val email: String,
    val password: String
) {
    companion object {
        fun getTenantRequestComplete(
            withEmail: String = "endryl@gmail.com",
            withPassword: String = "123"
        ): TenantRequestFixture {
            return TenantRequestFixture(
                email = withEmail,
                password = withPassword
            )
        }
    }

    fun build() = TenantRequest(
        email = email,
        password = password
    )
}