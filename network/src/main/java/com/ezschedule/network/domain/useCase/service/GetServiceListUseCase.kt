package com.ezschedule.network.domain.useCase.service

import com.ezschedule.network.data.repository.ServiceRepository

class GetServiceListUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(id: Int) = repository.getServicesList(id)
}