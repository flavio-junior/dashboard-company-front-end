package br.com.digital.store.utils

import br.com.digital.store.components.strings.StringsUtils.DELIVERED
import br.com.digital.store.components.strings.StringsUtils.NO
import br.com.digital.store.components.strings.StringsUtils.PENDING_DELIVERY
import br.com.digital.store.components.strings.StringsUtils.SENDING
import br.com.digital.store.components.strings.StringsUtils.YES
import br.com.digital.store.utils.NumbersUtils.NUMBER_EIGHTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_FIFTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_FORTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE_HUNDRED
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_TEN
import br.com.digital.store.utils.NumbersUtils.NUMBER_TWENTY

object CommonUtils {
    const val EMPTY_TEXT = ""
    const val WEIGHT_SIZE = 1f
    const val WEIGHT_SIZE_1_5 = 1.5f
    const val WEIGHT_SIZE_2 = 2f
    const val WEIGHT_SIZE_3 = 3f
    const val WEIGHT_SIZE_4 = 4f
    const val WEIGHT_SIZE_5 = 5f
    const val WEIGHT_SIZE_9 = 9f
    const val ZERO_DOUBLE = "0.0"
    const val UNAUTHORIZED_MESSAGE = "Token expired, please login again."
    const val NUMBER_EQUALS_ZERO = "Informe um valor maior que zero!"
}

fun checkNameIsNull(name: String): Boolean {
    return (name.isEmpty())
}

fun checkPriceIsNull(price: Double): Boolean {
    return (price == 0.0)
}

fun definitionState(state: Boolean): String {
    return if (state) YES else NO
}

val updateStatusDelivery = listOf(
    PENDING_DELIVERY,
    SENDING,
    DELIVERED
)

val deliveryStatus = listOf(
    PENDING_DELIVERY,
    DELIVERED
)

val sizeList = listOf(
    "10 Items",
    "20 Items",
    "40 Items",
    "50 Items",
    "60 Items",
    "80 Items",
    "100 Items"
)

fun converterSizeStringToInt(size: String): Int {
    return when (size) {
        "10 Items" -> NUMBER_TEN
        "20 Items" -> NUMBER_TWENTY
        "40 Items" -> NUMBER_FORTY
        "50 Items" -> NUMBER_FIFTY
        "60 Items" -> NUMBER_SIXTY
        "80 Items" -> NUMBER_EIGHTY
        "100 Items" -> NUMBER_ONE_HUNDRED
        else -> 60
    }
}

enum class LocationRoute {
    SEARCH,
    SORT,
    FILTER,
    RELOAD
}
