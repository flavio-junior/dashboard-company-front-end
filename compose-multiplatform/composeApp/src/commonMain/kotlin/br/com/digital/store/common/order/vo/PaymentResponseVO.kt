package br.com.digital.store.common.order.vo

import br.com.digital.store.common.order.others.PaymentStatus
import br.com.digital.store.common.order.others.PaymentType

data class PaymentResponseVO(
    var id: Long,
    var status: PaymentStatus? = null,
    var type: PaymentType? = null
)
