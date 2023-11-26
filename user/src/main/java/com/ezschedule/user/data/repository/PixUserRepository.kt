package com.ezschedule.user.data.repository

import com.ezschedule.network.data.api.PixEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixData
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.response.PixResponse

class PixUserRepository(private val endpoint: PixEndpoint) {
    suspend fun getPix(pix: PixRequest): ResultWrapper<PixResponse> {
        return requestHandler {
            endpoint.getPix(pix)
        }
    }

    suspend fun getAllPixAttempts(cpf: String): ResultWrapper<PixData> {
        return requestHandler {
            endpoint.getAllUserPixAttempts(cpf)
        }
    }
}