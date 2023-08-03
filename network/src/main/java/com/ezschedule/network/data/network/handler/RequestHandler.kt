package com.ezschedule.network.data.network.handler

import com.ezschedule.network.data.network.exception.ClientException
import com.ezschedule.network.data.network.exception.ServerException
import com.ezschedule.network.data.network.exception.UnknownResponseException
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import retrofit2.HttpException

suspend fun <T> requestHandler(function: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(function())
    } catch (e: HttpException) {
        ResultWrapper.Error(
            when (e.code()) {
                in 400..499 -> ClientException(code = e.code(), message = e.message)
                in 500..599 -> ServerException(code = e.code(), message = e.message)
                else -> UnknownResponseException(code = e.code(), message = e.message)
            }
        )
    }
}