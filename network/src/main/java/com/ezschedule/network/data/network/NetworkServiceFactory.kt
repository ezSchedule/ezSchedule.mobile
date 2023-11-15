package com.ezschedule.network.data.network

import android.content.Context
import com.ezschedule.network.data.network.interceptor.LoggingInterceptor
import com.ezschedule.utils.SharedPreferencesManager
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceFactory {
    inline fun <reified T> createNetworkService(context: Context): T {
        val token = SharedPreferencesManager(context).getInfo().tokenJWT

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(LoggingInterceptor(token))

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.115:8443/api/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client.build())
            .build()

        return retrofit.create(T::class.java)
    }
}