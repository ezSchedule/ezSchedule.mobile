package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.TenantLoginData
import com.ezschedule.network.domain.data.TenantLoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface TenantEndpoint {
    @POST("users/login")
    suspend fun singUp(@Body tenantRequest: TenantLoginRequest): TenantLoginData
}