package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.status.AddressStatus

data class AddressResponseVO(
    val id: Int? = 0,
    val status: AddressStatus? = null,
    val complement: String? = "",
    val district: String? = "",
    val number: Int? = 0,
    val street: String? = ""
)
