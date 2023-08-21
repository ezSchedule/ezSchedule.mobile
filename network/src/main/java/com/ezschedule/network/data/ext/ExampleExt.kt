package com.ezschedule.network.data.ext

// Example of how the Extension file with lists and class should be done
//fun List<TenantData>.toResponse() = TenantsResponse(
//    tenantList = this.map {
//        it.toResponse()
//    }
//)
//
//private fun TenantData.toResponse() = TenantResponse(
//    id = id.toString(),
//    name = name,
//    email = email,
//    password = password ?: ""
//)