package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.repository.TenantRepository
import com.ezschedule.network.domain.data.TenantUpdateRequest

class UpdateTenantSettingsUseCase(private val repository: TenantRepository) {
    suspend operator fun invoke(id: Int, tenantUpdate: TenantUpdateRequest) =
        repository.updateTenant(id, tenantUpdate)
}