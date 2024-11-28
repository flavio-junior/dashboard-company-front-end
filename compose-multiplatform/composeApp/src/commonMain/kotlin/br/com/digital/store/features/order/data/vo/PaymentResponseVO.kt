package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.data.others.PaymentStatus
import br.com.digital.store.features.order.data.others.PaymentType

data class PaymentResponseVO(
    var id: Long,
    var status: PaymentStatus? = null,
    var type: PaymentType? = null
)
