package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.repository.TenantRepository

class GetTenantByIdUseCase(private val repository: TenantRepository) {
    suspend operator fun invoke(id: Int) = repository.findById(id)
}