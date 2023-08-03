package com.ezschedule.network.data.network.exception

import java.lang.Exception

class ServerException(
    override val message: String?,
    val code: Int
) : Exception(message)