package br.com.digital.store.features.product.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePriceProductRequestDTO(
    val price: Double = 0.0,
)
