package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.TenantRepository
import com.ezschedule.network.domain.response.TenantResponse

class GetTenantByIdUseCase(private val repository: TenantRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<TenantResponse> {
        return repository.findById(id)
    }
}