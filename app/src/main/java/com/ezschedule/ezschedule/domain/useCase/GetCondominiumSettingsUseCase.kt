package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.CondominiumRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.CondominiumSettingsData

class GetCondominiumSettingsUseCase(private val repository: CondominiumRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<CondominiumSettingsData> =
        repository.getCondominiumSettings(id)

}