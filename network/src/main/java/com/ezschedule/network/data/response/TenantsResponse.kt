package com.ezschedule.network.data.response

data class TenantsResponse(
    val tenantList: List<TenantResponse>
) {
    // Aqui dentro nós devemos passar de Response para Presentation.
}