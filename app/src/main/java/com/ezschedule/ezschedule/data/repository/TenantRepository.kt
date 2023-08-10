package com.ezschedule.ezschedule.data.repository

import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.TenantResponse

class TenantRepository(private val endpoint: TenantEndpoint) {
    suspend fun login(tenant: TenantRequest): ResultWrapper<TenantResponse> {
        return requestHandler {
            endpoint.singUp(tenant).toResponse()
        }
    }
}