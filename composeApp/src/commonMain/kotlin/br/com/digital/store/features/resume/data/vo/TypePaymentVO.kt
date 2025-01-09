package br.com.digital.store.features.resume.data.vo

import br.com.digital.store.features.order.domain.type.TypeOrder

data class TypePaymentVO(
    val typeOrder: TypeOrder,
    val analise: List<DescriptionPaymentResponseVO>? = null,
    val numberOrders: Long,
    val total: Double,
    val discount: Long
)
