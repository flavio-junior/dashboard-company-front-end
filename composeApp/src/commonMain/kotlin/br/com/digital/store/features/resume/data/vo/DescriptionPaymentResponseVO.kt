package br.com.digital.store.features.resume.data.vo

import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.domain.type.TypeOrder

data class DescriptionPaymentResponseVO(
    val typeOrder: TypeOrder,
    val typePayment: PaymentType,
    val numberItems: Long,
    val total: Double,
    val discount: Long
)
