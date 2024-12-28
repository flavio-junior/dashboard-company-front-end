package br.com.digital.store.features.order.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressRequestDTO(
    val complement: String,
    val district: String,
    val number: Int,
    val street: String
)
