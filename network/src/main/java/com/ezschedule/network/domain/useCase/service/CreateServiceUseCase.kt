package com.ezschedule.network.domain.useCase.service

import com.ezschedule.network.data.repository.ServiceRepository
import com.ezschedule.network.domain.data.ServiceRequest

class CreateServiceUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(service: ServiceRequest) = repository.createService(service)
}