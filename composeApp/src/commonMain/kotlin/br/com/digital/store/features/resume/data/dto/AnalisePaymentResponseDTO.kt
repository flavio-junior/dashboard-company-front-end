package br.com.digital.store.features.resume.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AnalisePaymentResponseDTO(
    val analise: List<PaymentSummaryResumeResponseDTO>? = null,
    val total: Double,
    val discount: Long
)
