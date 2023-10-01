package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.TenantData
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.data.TenantSettingsData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface TenantEndpoint {
    @POST("users/login")
    suspend fun signUp(@Body tenantRequest: TenantRequest): TenantData

    @POST("users/logout/{email}")
    suspend fun signOut(@Path("email") email: String)

    @GET("users/{id}")
    suspend fun findUserById(@Path("id") id: Int): TenantSettingsData

    @PUT("users/update-tenant")
    @Multipart
    suspend fun updateTenantSettings(
        @Query("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("cpf") cpf: RequestBody,
        @Part("residentsBlock") residentsBlock: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("apartmentNumber") apartmentNumber: Int,
        @Part image: MultipartBody.Part?
    )

}