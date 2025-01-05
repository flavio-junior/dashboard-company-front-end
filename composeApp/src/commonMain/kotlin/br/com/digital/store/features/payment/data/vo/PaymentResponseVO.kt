package br.com.digital.store.features.payment.data.vo

import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.domain.type.TypeOrder

data class PaymentResponseVO(
    var id: Long = 0,
    var date: String? = null,
    var hour: String? = null,
    var code: Long? = null,
    var typeOrder: TypeOrder? = null,
    var typePayment: PaymentType? = null,
    var discount: Boolean? = null,
    var valueDiscount: Double? = null,
    var total: Double = 0.0
)
