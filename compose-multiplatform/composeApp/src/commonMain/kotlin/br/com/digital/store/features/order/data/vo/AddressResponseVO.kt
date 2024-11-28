package br.com.digital.store.features.order.data.vo

data class AddressResponseVO(
    val id: Int,
    val complement: String,
    val district: String,
    val number: Int,
    val street: String
)
