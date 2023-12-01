package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.repository.TenantRepository
import com.ezschedule.network.domain.data.TenantRequest

class LoginUseCase(private val repository: TenantRepository) {
    suspend operator fun invoke(tenant: TenantRequest) = repository.login(tenant)
}