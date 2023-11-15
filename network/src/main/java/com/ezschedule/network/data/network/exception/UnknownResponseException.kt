package com.ezschedule.network.data.network.exception

import java.lang.Exception

class UnknownResponseException(
    override val message: String?,
    val code: Int
) : Exception(message)