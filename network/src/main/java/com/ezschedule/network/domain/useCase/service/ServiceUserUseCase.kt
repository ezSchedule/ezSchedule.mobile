package com.ezschedule.network.domain.useCase.service

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.ServicesResponse
import com.ezschedule.network.data.repository.ServiceRepository

class ServiceUserUseCase(private val repository: ServiceRepository) {
    suspend fun execute(id: Int): ResultWrapper<ServicesResponse> {
        return repository.getServices(id)
    }
}