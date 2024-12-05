package br.com.digital.store.features.food.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePriceFoodRequestDTO(
    val price: Double = 0.0,
)
