package br.com.dashboard.company.utils.others

import br.com.dashboard.company.utils.common.TypeItem

fun converterTypeOrder(
    type: String? = ""
): TypeItem? {
    return when (type) {
        TypeItem.FOOD.name -> TypeItem.FOOD
        TypeItem.PRODUCT.name -> TypeItem.PRODUCT
        else -> null
    }
}
