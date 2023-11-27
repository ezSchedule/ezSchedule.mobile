package com.ezschedule.user.data.repository

import com.ezschedule.network.data.api.SaloonEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.SalonData

class SaloonUserRepository(private val endpoint: SaloonEndpoint) {
    suspend fun getSaloon(id: Int): ResultWrapper<List<SalonData>> {
        return requestHandler {
            endpoint.getSaloon(id)
        }
    }
}