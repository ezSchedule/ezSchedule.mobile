package com.ezschedule.ezschedule.data.repository

import com.ezschedule.network.data.api.CondominiumEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.CondominiumSettingsData

class CondominiumRepository(private val endpoint: CondominiumEndpoint) {

    suspend fun getCondominiumSettings(id: Int): ResultWrapper<CondominiumSettingsData> {
        return requestHandler {
            endpoint.getCondominiunSettings(id)
        }
    }

}