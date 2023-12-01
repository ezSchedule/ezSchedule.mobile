package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.repository.PixRepository

class UserGetAllPixAttemptsUseCase(private val repository: PixRepository) {
    suspend operator fun invoke(cpf: String) = repository.getAllUserPixAttempts(cpf)
}