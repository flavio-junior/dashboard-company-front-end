package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.utils.OrderUtils.CREDIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.DEBIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.MONEY
import br.com.digital.store.features.order.utils.OrderUtils.PIX

fun typePaymentFactory(
    payment: String
): PaymentType? {
    return when (payment) {
        CREDIT_CAD -> PaymentType.CREDIT_CAD
        DEBIT_CAD -> PaymentType.DEBIT_CAD
        MONEY -> PaymentType.MONEY
        PIX -> PaymentType.PIX
        else -> null
    }
}
