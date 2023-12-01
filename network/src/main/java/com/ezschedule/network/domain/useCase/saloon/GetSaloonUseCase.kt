package com.ezschedule.network.domain.useCase.saloon

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.SaloonRepository
import com.ezschedule.network.domain.data.SalonData

class GetSaloonUseCase(private val repository: SaloonRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<List<SalonData>> {
        return repository.getSaloon(id)
    }
}
