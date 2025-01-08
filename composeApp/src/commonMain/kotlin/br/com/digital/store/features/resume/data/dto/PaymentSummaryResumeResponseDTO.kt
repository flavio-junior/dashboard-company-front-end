package br.com.digital.store.features.resume.data.dto

import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.domain.type.TypeOrder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSummaryResumeResponseDTO(
    @SerialName("type_order")
    val typeOrder: TypeOrder,
    @SerialName("type_payment")
    val typePayment: PaymentType,
    val count: Long,
    val total: Double
)
