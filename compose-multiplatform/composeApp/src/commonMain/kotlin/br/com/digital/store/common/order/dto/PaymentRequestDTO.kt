package br.com.digital.store.common.order.dto

import br.com.digital.store.common.order.others.PaymentStatus
import br.com.digital.store.common.order.others.PaymentType
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequestDTO(
    var status: PaymentStatus? = null,
    var type: PaymentType? = null
)
