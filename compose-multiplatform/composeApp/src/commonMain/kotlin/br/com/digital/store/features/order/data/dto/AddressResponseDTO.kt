package br.com.digital.store.features.order.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponseDTO(
    val id: Int? = 0,
    val complement: String? = "",
    val district: String? = "",
    val number: Int? = 0,
    val street: String? = ""
)
