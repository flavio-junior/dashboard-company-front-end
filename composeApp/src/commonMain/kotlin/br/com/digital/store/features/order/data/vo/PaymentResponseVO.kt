package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.type.PaymentType

data class PaymentResponseVO(
    var id: Long? = 0,
    var createdAt: String? = "",
    var type: PaymentType? = null
)
