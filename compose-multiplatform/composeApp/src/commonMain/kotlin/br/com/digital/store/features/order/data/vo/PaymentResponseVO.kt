package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.status.PaymentStatus
import br.com.digital.store.features.order.domain.type.PaymentType

data class PaymentResponseVO(
    var id: Long? = 0,
    var createdAt: String? = "",
    var status: PaymentStatus? = null,
    var type: PaymentType? = null
)
