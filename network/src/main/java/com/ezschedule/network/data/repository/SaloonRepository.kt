package com.ezschedule.network.data.repository

import com.ezschedule.network.data.api.SaloonEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.SalonData
import com.ezschedule.network.domain.data.SaloonRequest

class SaloonRepository(private val endpoint: SaloonEndpoint) {
    suspend fun createSaloon(saloon: SaloonRequest): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.createSaloon(saloon)
        }
    }

    suspend fun getSaloon(id: Int): ResultWrapper<List<SalonData>> {
        return requestHandler {
            endpoint.getSaloon(id)
        }
    }
}