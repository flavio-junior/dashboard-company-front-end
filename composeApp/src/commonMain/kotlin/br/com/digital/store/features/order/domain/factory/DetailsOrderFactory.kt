package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.components.strings.StringsUtils.DELIVERY
import br.com.digital.store.components.strings.StringsUtils.EMPTY_TYPE
import br.com.digital.store.components.strings.StringsUtils.ORDER
import br.com.digital.store.components.strings.StringsUtils.RESERVATION
import br.com.digital.store.features.order.domain.type.TypeOrder

fun detailsOrderFactory(type: TypeOrder? = null): String {
    return when (type) {
        TypeOrder.DELIVERY -> DELIVERY
        TypeOrder.RESERVATION -> RESERVATION
        TypeOrder.ORDER -> ORDER
        null -> EMPTY_TYPE
    }
}
