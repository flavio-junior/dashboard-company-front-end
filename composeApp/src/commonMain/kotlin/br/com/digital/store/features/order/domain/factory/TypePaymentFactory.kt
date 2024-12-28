package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.features.order.domain.type.PaymentType

fun typePaymentFactory(
    payment: String
): PaymentType? {
    return when (payment) {
        "Cartão de Crédito" -> PaymentType.CREDIT_CAD
        "Cartão de Débito" -> PaymentType.DEBIT_CAD
        "Dinheiro" -> PaymentType.MONEY
        "PIX" -> PaymentType.PIX
        else -> null
    }
}
