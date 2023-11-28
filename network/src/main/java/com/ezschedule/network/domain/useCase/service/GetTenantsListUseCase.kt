package com.ezschedule.network.domain.useCase.service

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ServiceRepository
import com.ezschedule.network.domain.response.TenantResponse

class GetTenantsListUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<List<TenantResponse>> =
        repository.getTenantsList(id)

}