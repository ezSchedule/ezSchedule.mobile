package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.ServiceData
import com.ezschedule.network.domain.data.ServiceRequest
import com.ezschedule.network.domain.data.TenantServiceData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ServicesEndpoint {
    @GET("services/tenant")
    suspend fun tenantsList(@Query("id") id: Int): List<TenantServiceData>

    @GET("services/condominium/{id}")
    suspend fun servicesList(@Path("id") id: Int): List<ServiceData>

    @POST("services")
    suspend fun createService(@Body service: ServiceRequest)

}