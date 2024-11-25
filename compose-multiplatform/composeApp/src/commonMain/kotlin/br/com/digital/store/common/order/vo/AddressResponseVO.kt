package br.com.digital.store.common.order.vo

data class AddressResponseVO(
    val id: Int,
    val complement: String,
    val district: String,
    val number: Int,
    val street: String
)
