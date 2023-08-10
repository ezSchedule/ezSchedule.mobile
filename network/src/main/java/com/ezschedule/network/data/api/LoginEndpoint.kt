package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.LoginData
import com.ezschedule.network.domain.data.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginEndpoint {
    @POST("users/login")
    suspend fun singUp(@Body tenantRequest: LoginRequest): LoginData
}