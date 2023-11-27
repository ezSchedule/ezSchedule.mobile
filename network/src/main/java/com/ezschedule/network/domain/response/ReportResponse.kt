package com.ezschedule.network.domain.response

data class ReportResponse(
    var id: String,
    val productName: String,
    val paymentStatus: String,
    var paymentDate: String,
    val invoiceNumber: String,
    val condominiumId: Int,
    var qrCode: String,
    var imageQrcode: String,
    val saloon: SaloonReportResponse,
    val tenant: TenantReportResponse,
    val schedule: ScheduleReportResponse
)
