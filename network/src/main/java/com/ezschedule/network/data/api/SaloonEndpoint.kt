package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.SalonData
import com.ezschedule.network.domain.data.SaloonRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SaloonEndpoint {

    @POST("saloons")
    suspend fun createSaloon(@Body saloon: SaloonRequest)

    @GET("saloons/condominium/{id}")
    suspend fun getSaloon(@Path("id") id: Int): List<SalonData>
}