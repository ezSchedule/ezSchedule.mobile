package com.ezschedule.network.data.network

import com.ezschedule.network.data.network.interceptor.LoggingInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceFactory {
    inline fun <reified T> createNetworkService(): T {
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(LoggingInterceptor())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://44.194.143.225:8080/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client.build())
            .build()

        return retrofit.create(T::class.java)
    }
}