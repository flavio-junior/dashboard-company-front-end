package br.com.digital.store.features.item.utils

object ItemsUtils {
    fun checkBodyItemIsNull(name: String, price: Double): Boolean {
        return (name.isEmpty() && price == 0.0)
    }
    fun checkPriceIsEqualsZero(price: Double): Boolean {
        return (price == 0.0)
    }
}
