package com.ezschedule.user.data.repository

import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.data.TenantUpdateRequest
import com.ezschedule.network.domain.response.TenantResponse
import okhttp3.RequestBody.Companion.toRequestBody

class TenantUserRepository(private val endpoint: TenantEndpoint) {

    suspend fun findById(id: Int): ResultWrapper<TenantResponse> {
        return requestHandler {
            endpoint.findUserById(id).toResponse()
        }
    }


}