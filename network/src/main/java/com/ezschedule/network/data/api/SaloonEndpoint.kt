package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.SaloonRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SaloonEndpoint {

    @POST("saloons")
    suspend fun createSaloon(@Body saloon: SaloonRequest)
}