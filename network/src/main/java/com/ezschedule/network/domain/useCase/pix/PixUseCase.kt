package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.repository.PixRepository
import com.ezschedule.network.domain.data.PixRequest

class PixUseCase(private val repository: PixRepository) {
    suspend operator fun invoke(pix: PixRequest) = repository.getPix(pix)
}