package com.ezschedule.ezschedule.fixture.presentation

import com.ezschedule.network.domain.presentation.TenantPresentation

class TenantPresentationFixture(
    val id: Int,
    val email: String,
    val name: String,
    val tokenJWT: String,
    val idCondominium: Int
) {
    companion object {
        fun getTenantPresentationComplete(
            withId: Int = 1,
            withEmail: String = "endryl@gmail.com",
            withName: String = "endryl fiorotti",
            withTokenJWT: String = "token",
            withIdCondominium: Int = 1
        ): TenantPresentationFixture {
            return TenantPresentationFixture(
                id = withId,
                email = withEmail,
                name = withName,
                tokenJWT = withTokenJWT,
                idCondominium = withIdCondominium
            )
        }
    }

    fun build() = TenantPresentation(
        id = id,
        email = email,
        name = name,
        tokenJWT = tokenJWT,
        idCondominium = idCondominium
    )
}