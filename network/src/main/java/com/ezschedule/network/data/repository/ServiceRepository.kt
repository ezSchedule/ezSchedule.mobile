package com.ezschedule.network.data.repository

import com.ezschedule.network.data.api.ServicesEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ServiceRequest
import com.ezschedule.network.domain.response.ServicesResponse
import com.ezschedule.network.domain.response.TenantResponse

class ServiceRepository(private val endpoint: ServicesEndpoint) {
    suspend fun getTenantsList(id: Int): ResultWrapper<List<TenantResponse>> {
        return requestHandler {
            endpoint.tenantsList(id).filter { it.isAdmin != 1 }
                .map { it.toResponse() }
        }
    }

    suspend fun getServicesList(id: Int): ResultWrapper<ServicesResponse> {
        return requestHandler {
            endpoint.servicesList(id).toResponse()
        }
    }

    suspend fun createService(service: ServiceRequest): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.createService(service)
        }
    }

    suspend fun getServices(id: Int): ResultWrapper<ServicesResponse> {
        return requestHandler {
            endpoint.servicesList(id).toResponse()
        }
    }
}