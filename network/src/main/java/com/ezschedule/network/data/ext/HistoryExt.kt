package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.HistoryData
import com.ezschedule.network.domain.data.SaloonHistoryData
import com.ezschedule.network.domain.data.ScheduleHistoryData
import com.ezschedule.network.domain.data.TenantHistoryData
import com.ezschedule.network.domain.response.HistoryResponse
import com.ezschedule.network.domain.response.SaloonHistoryResponse
import com.ezschedule.network.domain.response.ScheduleHistoryResponse
import com.ezschedule.network.domain.response.TenantHistoryResponse
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

fun QuerySnapshot.toHistory() = this.map {
    it.toHistoryObject()
}.toHistoryResponse()

fun QueryDocumentSnapshot.toHistoryObject() = HistoryData(
    id = this.getString("id"),
    invoiceNumber = this.getString("invoiceNumber"),
    paymentStatus = this.getString("paymentStatus"),
    productName = this.getString("productName"),
    qrCode = this.getString("qrCode"),
    imageQrcode = this.getString("imageQrcode"),
    condominiumId = this.getLong("condominiumId")?.toInt(),
    paymentDate = this.getString("paymentDate"),
    saloon = SaloonHistoryData(
        id = this.getLong("saloon.id")?.toInt(),
        name = this.getString("saloon.name"),
        saloonPrice = this.getDouble("saloon.saloonPrice"),
        blockEvent = this.getString("saloon.blockEvent")
    ),
    schedule = ScheduleHistoryData(
        id = this.getLong("schedule.id")?.toInt(),
        nameEvent = this.getString("schedule.nameEvent"),
        typeEvent = this.getString("schedule.typeEvent"),
        totalNumberGuests = this.getLong("schedule.totalNumberGuests")?.toInt(),
        isCanceled = this.getBoolean("schedule.isCanceled"),
        dateEvent = this.getString("schedule.dateEvent")
    ),
    tenant = TenantHistoryData(
        id = this.getLong("tenant.id")?.toInt(),
        name = this.getString("tenant.name"),
        phoneNumber = this.getString("tenant.phoneNumber"),
        unit = this.getString("tenant.unit"),
        apartmentNumber = this.getLong("tenant.apartmentNumber")
    )
)

fun List<HistoryData>.toHistoryResponse() = this.map {
    it.toHistoryResponse()
}

fun HistoryData.toHistoryResponse() = HistoryResponse(
    id = id ?: "",
    invoiceNumber = invoiceNumber ?: "",
    paymentStatus = paymentStatus ?: "",
    productName = productName ?: "",
    qrCode = qrCode ?: "",
    imageQrcode = imageQrcode ?: "",
    condominiumId = condominiumId ?: 0,
    paymentDate = paymentDate,
    saloon = SaloonHistoryResponse(
        id = saloon.id ?: 0,
        name = saloon.name ?: "",
        saloonPrice = saloon.saloonPrice ?: 0.0,
        blockEvent = saloon.blockEvent ?: ""
    ),
    schedule = ScheduleHistoryResponse(
        id = schedule.id ?: 0,
        nameEvent = schedule.nameEvent ?: "",
        typeEvent = schedule.typeEvent ?: "",
        totalNumberGuests = schedule.totalNumberGuests ?: 0,
        isCanceled = schedule.isCanceled ?: true,
        dateEvent = schedule.dateEvent ?: ""
    ),
    tenant = TenantHistoryResponse(
        id = tenant.id ?: 0,
        name = tenant.name ?: "",
        phoneNumber = tenant.phoneNumber ?: "",
        unit = tenant.unit ?: "",
        apartmentNumber = tenant.apartmentNumber ?: 0
    )
)