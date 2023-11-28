package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.repository.TenantRepository
import com.ezschedule.network.domain.data.TenantUpdateRequest

class UpdateTenantSettingsUseCase(val repository: TenantRepository) {

    suspend operator fun invoke(id: Int, tenantUpdateRequest: TenantUpdateRequest) =
        repository.updateTenant(id, tenantUpdateRequest)

}