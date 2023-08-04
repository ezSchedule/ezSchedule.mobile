package com.ezschedule.network.data.api

import com.ezschedule.network.data.data.TenantData
import retrofit2.http.GET

interface TenantEndpoint {
    @GET("users")
    suspend fun getTenants(): List<TenantData>
}