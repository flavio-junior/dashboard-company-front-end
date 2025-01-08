package br.com.digital.store.features.resume.data.vo

import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.domain.type.TypeOrder

data class PaymentSummaryResponseVO(
    val typeOrder: TypeOrder,
    val typePayment: PaymentType,
    val count: Long,
    val total: Double
)
