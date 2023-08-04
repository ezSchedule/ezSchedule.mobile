package com.ezschedule.network.data.ext

import com.ezschedule.network.data.data.TenantData
import com.ezschedule.network.data.response.TenantResponse
import com.ezschedule.network.data.response.TenantsResponse

fun List<TenantData>.toResponse() = TenantsResponse(
    tenantList = this.map {
        it.toResponse()
    }
)

private fun TenantData.toResponse() = TenantResponse(
    id = id.toString(),
    name = name,
    email = email,
    password = password ?: ""
)