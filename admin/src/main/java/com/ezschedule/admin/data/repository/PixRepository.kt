package com.ezschedule.admin.data.repository

import com.ezschedule.network.data.api.PixEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixData

class PixRepository(private val endpoint: PixEndpoint) {

    suspend fun getAllPixAttempts(): ResultWrapper<PixData> {
        return requestHandler {
            endpoint.getAllPixAttempts()
        }
    }
}