package com.ezschedule.user.domain.useCase

import com.ezschedule.user.data.repository.SaloonUserRepository

class GetSaloonUseCase(private val repository: SaloonUserRepository) {
    suspend operator fun invoke(id: Int) = repository.getSaloon(id)
}
