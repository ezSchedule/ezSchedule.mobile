package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.PixRepository
import com.ezschedule.network.domain.data.PixData

class GetAllPixAttempts(private val repository: PixRepository) {
    suspend operator fun invoke(): ResultWrapper<PixData> =
        repository.getAllPixAttempts()

}