package br.com.digital.store.features.fee.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePriceFeeRequestDTO(
    val price: Double
)
