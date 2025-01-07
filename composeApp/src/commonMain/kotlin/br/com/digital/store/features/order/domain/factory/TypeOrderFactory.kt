package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.components.strings.StringsUtils.FOOD
import br.com.digital.store.components.strings.StringsUtils.ITEM
import br.com.digital.store.components.strings.StringsUtils.PRODUCT
import br.com.digital.store.features.order.domain.type.TypeItem
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

fun typeOrderFactory(type: TypeItem? = null): String {
    return when (type) {
        TypeItem.FOOD -> FOOD
        TypeItem.PRODUCT -> PRODUCT
        TypeItem.ITEM -> ITEM
        else -> EMPTY_TEXT
    }
}
