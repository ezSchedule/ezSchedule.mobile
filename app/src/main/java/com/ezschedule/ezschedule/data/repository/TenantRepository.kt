package com.ezschedule.ezschedule.data.repository

import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.data.TenantUpdateRequest
import com.ezschedule.network.domain.response.TenantResponse
import okhttp3.RequestBody.Companion.toRequestBody

class TenantRepository(private val endpoint: TenantEndpoint) {
    suspend fun login(tenant: TenantRequest): ResultWrapper<TenantResponse> {
        return requestHandler {
            endpoint.signUp(tenant).toResponse()
        }
    }

    suspend fun logout(email: String): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.signOut(email)
        }
    }

    suspend fun findById(id: Int): ResultWrapper<TenantResponse> {
        return requestHandler {
            endpoint.findUserById(id).toResponse()
        }
    }

    suspend fun updateTenant(
        id: Int,
        tenantUpdateRequest: TenantUpdateRequest
    ): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.updateTenantSettings(
                id = id,
                name = tenantUpdateRequest.name.toRequestBody(),
                email = tenantUpdateRequest.email.toRequestBody(),
                cpf = tenantUpdateRequest.cpf.toRequestBody(),
                residentsBlock = tenantUpdateRequest.residentsBlock.toRequestBody(),
                phoneNumber = tenantUpdateRequest.phoneNumber.toRequestBody(),
                apartmentNumber = tenantUpdateRequest.apartmentNumber,
                params = tenantUpdateRequest.image
            )
        }
    }

}