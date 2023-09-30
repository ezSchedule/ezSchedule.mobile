package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.TenantData
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.data.TenantSettingsData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TenantEndpoint {
    @POST("users/login")
    suspend fun signUp(@Body tenantRequest: TenantRequest): TenantData

    @POST("users/logout/{email}")
    suspend fun signOut(@Path("email") email: String)

    @GET("users/{id}")
    suspend fun findUserById(@Path("id") id: Int): TenantSettingsData

}