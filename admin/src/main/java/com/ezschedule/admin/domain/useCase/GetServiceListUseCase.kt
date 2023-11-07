package com.ezschedule.admin.domain.useCase

import com.ezschedule.admin.data.repository.ServiceRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.ServicesResponse

class GetServiceListUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<ServicesResponse> =
        repository.getServicesList(id)

}