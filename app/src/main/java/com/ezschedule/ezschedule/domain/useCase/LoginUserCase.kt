package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.network.domain.data.TenantLoginRequest
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.TenantLoginResponse

class LoginUserCase(private val repository: TenantRepository) {
    suspend fun execute(tenant: TenantLoginRequest): ResultWrapper<TenantLoginResponse> {
        return repository.login(tenant)
    }
}