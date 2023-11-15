package com.ezschedule.ezschedule.data.repository

import com.ezschedule.network.data.api.SaloonEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.SaloonRequest

class SaloonRepository(private val endpoint: SaloonEndpoint) {
    suspend fun createSaloon(saloon: SaloonRequest): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.createSaloon(saloon)
        }
    }
}