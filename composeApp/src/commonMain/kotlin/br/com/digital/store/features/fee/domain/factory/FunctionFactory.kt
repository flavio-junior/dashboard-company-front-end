package br.com.digital.store.features.fee.domain.factory

import br.com.digital.store.components.strings.StringsUtils.ATTENDANT
import br.com.digital.store.components.strings.StringsUtils.BOX
import br.com.digital.store.components.strings.StringsUtils.WAITER
import br.com.digital.store.features.fee.domain.type.Function
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

fun functionFactory(function: Function? = null): String {
    return when (function) {
        Function.ATTENDANT -> ATTENDANT
        Function.BOX -> BOX
        Function.WAITER -> WAITER
        null -> EMPTY_TEXT
    }
}
