package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.ServiceData
import com.ezschedule.network.domain.data.TenantData
import com.ezschedule.network.domain.data.TenantServiceData
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.response.TenantResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServicesEndpoint {
    @GET("services/tenant")
    suspend fun tenantsList(@Query("id") id: Int): List<TenantServiceData>

    @GET("services/condominium/{id}")
    suspend fun servicesList(@Path("id") id: Int): List<ServiceData>


}