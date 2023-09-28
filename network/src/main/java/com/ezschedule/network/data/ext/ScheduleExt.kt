package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.calendar.ScheduleData
import com.ezschedule.network.domain.response.ScheduleResponse
import com.ezschedule.network.domain.response.SchedulesResponse

fun List<ScheduleData>.toResponse() = SchedulesResponse(
    schedules = this.map {
        it.toResponse()
    }
)

fun ScheduleData.toResponse() = ScheduleResponse(
    id = id,
    name = name,
    type = type ?: "",
    date = date,
    isCanceled = isCanceled == 1,
    numberGuests = numberGuests,
    idSalon = salonData?.id ?: 0,
    nameSalon = salonData?.name ?: "",
    priceSalon = salonData?.price ?: 0.0,
    blockSalon = salonData?.block ?: "",
    idTenant = tenantData.id,
    nameTenant = tenantData.name
)