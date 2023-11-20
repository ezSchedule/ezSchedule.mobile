package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.EventItemPresentation
import com.ezschedule.network.domain.presentation.SchedulesPresentation

data class ScheduleResponse(
    val id: Int,
    val name: String,
    val type: String,
    val date: String,
    val isCanceled: Boolean,
    val numberGuests: Int,
    val idSalon: Int?,
    val nameSalon: String?,
    val priceSalon: Double?,
    val blockSalon: String?,
    val idTenant: Int,
    val nameTenant: String,
) {
    fun toEventPresentation() = EventItemPresentation(
        date = date,
        isCanceled = isCanceled,
        nameEvent = name,
        nameTenant = nameTenant,
        nameSalon = nameSalon
    )

    fun toSchedulePresentation() = SchedulesPresentation(
        salon = nameSalon ?: "",
        event = name,
        date = date,
        peoples = numberGuests
    )
}