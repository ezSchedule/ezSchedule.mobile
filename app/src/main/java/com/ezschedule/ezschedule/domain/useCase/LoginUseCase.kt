package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.TenantResponse

class LoginUseCase(private val repository: TenantRepository) {
    suspend fun execute(tenant: TenantRequest): ResultWrapper<TenantResponse> {
        return repository.login(tenant)
    }
}