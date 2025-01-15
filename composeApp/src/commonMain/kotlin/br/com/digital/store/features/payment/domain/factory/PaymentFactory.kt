package br.com.digital.store.features.payment.domain.factory

import br.com.digital.store.components.strings.StringsUtils.ORDER
import br.com.digital.store.components.strings.StringsUtils.PICKUP
import br.com.digital.store.components.strings.StringsUtils.RESERVATION
import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.utils.OrderUtils.BUY_PRODUCTS
import br.com.digital.store.features.order.utils.OrderUtils.CREDIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.CREDIT_CAD_LATIN
import br.com.digital.store.features.order.utils.OrderUtils.DEBIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.DEBIT_CAD_LATIN
import br.com.digital.store.features.order.utils.OrderUtils.DELIVERY
import br.com.digital.store.features.order.utils.OrderUtils.MONEY
import br.com.digital.store.features.order.utils.OrderUtils.PIX
import br.com.digital.store.features.order.utils.OrderUtils.SHOPPING_CART
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

fun paymentTypeFactory(
    payment: PaymentType? = null
): String {
    return when (payment) {
        PaymentType.CREDIT_CAD -> CREDIT_CAD
        PaymentType.DEBIT_CAD -> DEBIT_CAD
        PaymentType.MONEY -> MONEY
        PaymentType.PIX -> PIX
        null -> EMPTY_TEXT
    }
}

fun paymentTypeFactoryLatin(
    payment: PaymentType? = null
): String {
    return when (payment) {
        PaymentType.CREDIT_CAD -> CREDIT_CAD_LATIN
        PaymentType.DEBIT_CAD -> DEBIT_CAD_LATIN
        PaymentType.MONEY -> MONEY
        PaymentType.PIX -> PIX
        null -> EMPTY_TEXT
    }
}

fun typeOrderFactory(
    type: TypeOrder? = null
): String {
    return when (type) {
        TypeOrder.DELIVERY -> DELIVERY
        TypeOrder.ORDER -> ORDER
        TypeOrder.RESERVATION -> RESERVATION
        TypeOrder.SHOPPING_CART -> SHOPPING_CART
        null -> EMPTY_TEXT
    }
}

fun typeOrderFactoryResponse(
    type: TypeOrder? = null
): String {
    return when (type) {
        TypeOrder.DELIVERY -> DELIVERY
        TypeOrder.ORDER -> PICKUP
        TypeOrder.RESERVATION -> RESERVATION
        TypeOrder.SHOPPING_CART -> BUY_PRODUCTS
        null -> EMPTY_TEXT
    }
}