package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.response.TenantsResponse

class GetTenantsUseCase(private val repository: TenantRepository) {
    suspend fun execute(): ResultWrapper<TenantsResponse> {
        return repository.getTenants()
    }
}