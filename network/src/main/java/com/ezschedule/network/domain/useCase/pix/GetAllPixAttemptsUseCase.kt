package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.repository.PixRepository

class GetAllPixAttemptsUseCase(private val repository: PixRepository) {
    suspend operator fun invoke() = repository.getAllPixAttempts()
}