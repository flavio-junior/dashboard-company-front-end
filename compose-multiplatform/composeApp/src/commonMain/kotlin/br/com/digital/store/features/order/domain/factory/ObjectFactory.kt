package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.components.strings.StringsUtils.DELIVERED
import br.com.digital.store.components.strings.StringsUtils.EMPTY_STATUS
import br.com.digital.store.components.strings.StringsUtils.PENDING_DELIVERY
import br.com.digital.store.features.order.domain.status.ObjectStatus

fun objectFactory(status: ObjectStatus? = null): String {
    return when (status) {
        ObjectStatus.PENDING -> PENDING_DELIVERY
        ObjectStatus.DELIVERED -> DELIVERED
        null -> EMPTY_STATUS
    }
}
