package br.com.digital.store.utils

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
    const val WEIGHT_SIZE_2 = 2f
    const val WEIGHT_SIZE_3 = 3f
    const val WEIGHT_SIZE_4 = 4f
    const val WEIGHT_SIZE_5 = 5f
    const val WEIGHT_SIZE_9 = 9f
    const val SIZE_DEFAULT = "60 Items"
    const val ZERO_DOUBLE = "0.0"
    const val MESSAGE_ZERO_DOUBLE = "O preço precisa ser maior que 0!"
    const val DELIVERY = "Delivery"
    const val RESERVATIONS = "Reservas"
    const val ORDERS = "Pedidos"
}

fun checkNameIsNull(name: String): Boolean {
    return (name.isEmpty())
}

fun checkPriceIsNull(price: Double): Boolean {
    return (price == 0.0)
}

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
