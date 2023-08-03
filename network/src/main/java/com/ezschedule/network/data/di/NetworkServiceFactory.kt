package com.ezschedule.network.data.di

import com.ezschedule.network.data.network.interceptor.LoggingInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceFactory {
    inline fun <reified T> createNetworkService(): T {
        val log = LoggingInterceptor().getInterceptor()
        val client = OkHttpClient().newBuilder().addInterceptor(log)
        val retrofit = Retrofit.Builder()
            .baseUrl("localhost:8080")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(T::class.java)
    }
}