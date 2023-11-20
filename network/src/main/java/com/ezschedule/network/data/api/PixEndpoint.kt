package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.response.PixResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PixEndpoint {
    @POST("pix")
    suspend fun getPix(@Body pix: PixRequest): PixResponse

}