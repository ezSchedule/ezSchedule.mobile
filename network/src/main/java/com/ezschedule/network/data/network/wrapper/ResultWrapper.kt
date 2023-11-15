package com.ezschedule.network.data.network.wrapper

sealed class ResultWrapper<out T> {
    data class Success<out T>(val content: T) : ResultWrapper<T>()

    data class Error(val error: Exception) : ResultWrapper<Nothing>()
}