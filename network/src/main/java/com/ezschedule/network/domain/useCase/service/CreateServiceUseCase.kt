package com.ezschedule.network.domain.useCase.service

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ServiceRepository
import com.ezschedule.network.domain.data.ServiceRequest

class CreateServiceUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(service: ServiceRequest): ResultWrapper<Unit> =
        repository.createService(service)

}