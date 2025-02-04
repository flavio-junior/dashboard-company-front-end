package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.type.PaymentType
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequestDTO(
    var type: PaymentType? = null,
    var discount: Boolean? = null,
    var value: Double? = 0.0,
    var remove: Boolean = false
)
