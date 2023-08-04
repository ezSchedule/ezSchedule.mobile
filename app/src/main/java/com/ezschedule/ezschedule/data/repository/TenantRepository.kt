package com.ezschedule.ezschedule.data.repository

import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.response.TenantsResponse

class TenantRepository(private val endpoint: TenantEndpoint) {
    suspend fun getTenants(): ResultWrapper<TenantsResponse> {
        return requestHandler {
            endpoint.getTenants().toResponse()
        }
    }
}