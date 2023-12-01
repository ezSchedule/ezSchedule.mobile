package com.ezschedule.network.domain.useCase.service

import com.ezschedule.network.data.repository.ServiceRepository

class GetTenantsListUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(id: Int) = repository.getTenantsList(id)
}