package br.com.digital.store.features.resume.data.vo

data class AnalisePaymentResponseVO(
    val analise: List<PaymentSummaryResponseVO>?,
    val total: Double,
    val discount: Long
)
