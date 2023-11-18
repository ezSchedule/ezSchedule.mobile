package com.ezschedule.network.domain.response

data class ReportResponse(
    var id: String,
    val productName: String,
    val paymentStatus: String,
    val invoiceNumber: String,
    val condominiumId: Int,
    val saloon: SaloonReportResponse,
    val tenant: TenantReportResponse,
    val schedule: ScheduleReportResponse
)
