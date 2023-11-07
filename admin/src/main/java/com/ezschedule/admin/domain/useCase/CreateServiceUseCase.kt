package com.ezschedule.admin.domain.useCase

import com.ezschedule.admin.data.repository.ServiceRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ServiceRequest

class CreateServiceUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(service: ServiceRequest): ResultWrapper<Unit> =
        repository.createService(service)

}