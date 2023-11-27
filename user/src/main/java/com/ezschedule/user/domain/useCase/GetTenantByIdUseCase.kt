package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.TenantResponse
import com.ezschedule.user.data.repository.TenantUserRepository

class GetTenantByIdUseCase(private val repository: TenantUserRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<TenantResponse> = repository.findById(id)

}