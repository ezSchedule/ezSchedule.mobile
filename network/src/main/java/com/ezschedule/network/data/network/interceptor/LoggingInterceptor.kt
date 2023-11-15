package com.ezschedule.network.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor(token: String) : Interceptor {
    private val internToken = token

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $internToken")
                .build()
        )
    }
}