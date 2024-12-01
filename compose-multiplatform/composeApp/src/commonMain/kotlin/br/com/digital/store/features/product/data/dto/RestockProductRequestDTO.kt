package br.com.digital.store.features.product.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RestockProductRequestDTO(
    val quantity: Int = 0
)
