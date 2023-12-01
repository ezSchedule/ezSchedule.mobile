package com.ezschedule.network.domain.useCase.saloon

import com.ezschedule.network.data.repository.SaloonRepository

class GetSaloonUseCase(private val repository: SaloonRepository) {
    suspend operator fun invoke(id: Int) = repository.getSaloon(id)
}
