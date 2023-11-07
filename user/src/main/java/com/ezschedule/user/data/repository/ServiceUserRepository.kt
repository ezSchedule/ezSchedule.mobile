package com.ezschedule.user.data.repository

import com.ezschedule.network.data.api.ServicesEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.ServicesResponse

class ServiceUserRepository(private val endpoint: ServicesEndpoint) {
    suspend fun getServices(id: Int): ResultWrapper<ServicesResponse> {
        return requestHandler {
            endpoint.servicesList(id).toResponse()
        }
    }
}