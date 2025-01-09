package br.com.digital.store.features.resume.data.dto

import br.com.digital.store.features.order.domain.type.TypeOrder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypePaymentDTO(
    @SerialName("type_order")
    val typeOrder: TypeOrder,
    val analise: List<DescriptionPaymentResponseDTO>,
    @SerialName("number_orders")
    val numberOrders: Long,
    val total: Double,
    val discount: Long
)
