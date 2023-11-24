package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.PixData
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.response.PixResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PixEndpoint {
    @POST("pix")
    suspend fun getPix(@Body pix: PixRequest): PixResponse

    @GET("pix/list/{cpf}")
    suspend fun getAllPixAttempts(@Path("cpf") cpf: String): PixData

}