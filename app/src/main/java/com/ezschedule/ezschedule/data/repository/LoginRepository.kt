package com.ezschedule.ezschedule.data.repository

import com.ezschedule.network.data.api.LoginEndpoint
import com.ezschedule.network.domain.data.LoginRequest
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.LoginResponse

class LoginRepository(private val endpoint: LoginEndpoint) {
    suspend fun login(tenant: LoginRequest): ResultWrapper<LoginResponse> {
        return requestHandler {
            endpoint.singUp(tenant).toResponse()
        }
    }
}