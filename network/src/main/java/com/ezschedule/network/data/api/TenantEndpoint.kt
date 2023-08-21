package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.TenantData
import com.ezschedule.network.domain.data.TenantRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TenantEndpoint {
    @POST("users/login")
    suspend fun singUp(@Body tenantRequest: TenantRequest): TenantData

    @POST("users/logout/{email}")
    suspend fun singOut(@Path("email") email: String)
}