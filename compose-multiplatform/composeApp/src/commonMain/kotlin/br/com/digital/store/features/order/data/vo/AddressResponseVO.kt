package br.com.digital.store.features.order.data.vo

data class AddressResponseVO(
    val id: Int? = 0,
    val complement: String? = "",
    val district: String? = "",
    val number: Int? = 0,
    val street: String? = ""
)
