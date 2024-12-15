package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.components.strings.StringsUtils.FOOD
import br.com.digital.store.components.strings.StringsUtils.ITEM
import br.com.digital.store.components.strings.StringsUtils.PRODUCT
import br.com.digital.store.features.order.domain.type.TypeItem

fun typeOrderFactory(type: TypeItem): String {
    return when (type) {
        TypeItem.FOOD -> FOOD
        TypeItem.PRODUCT -> PRODUCT
        TypeItem.ITEM -> ITEM
    }
}
