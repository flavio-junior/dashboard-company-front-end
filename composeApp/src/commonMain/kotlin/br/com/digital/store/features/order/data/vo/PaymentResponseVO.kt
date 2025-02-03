package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.domain.type.TypeOrder

data class PaymentResponseVO(
    var id: Long = 0,
    var date: String? = "",
    var hour: String? = "",
    var code: Long? = 0,
    var author: String? = "",
    var assigned: String? = "",
    var typeOrder: TypeOrder? = null,
    var typePayment: PaymentType? = null,
    var discount: Boolean? = null,
    var valueDiscount: Double? = null,
    var fee: Boolean? = null,
    var valueFee: Double? = null,
    var total: Double = 0.0
)
