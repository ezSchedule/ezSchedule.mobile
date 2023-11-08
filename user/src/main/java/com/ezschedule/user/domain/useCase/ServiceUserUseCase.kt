package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.ServicesResponse
import com.ezschedule.user.data.repository.ServiceUserRepository

class ServiceUserUseCase(private val repository: ServiceUserRepository) {
    suspend fun execute(id: Int): ResultWrapper<ServicesResponse> {
        return repository.getServices(id)
    }
}