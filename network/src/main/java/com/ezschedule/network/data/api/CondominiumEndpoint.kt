package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.CondominiumSettingsData
import retrofit2.http.GET
import retrofit2.http.Query

interface CondominiumEndpoint {

    @GET("condominium/settings")
    suspend fun getCondominiunSettings(@Query("id") id: Int): CondominiumSettingsData
}