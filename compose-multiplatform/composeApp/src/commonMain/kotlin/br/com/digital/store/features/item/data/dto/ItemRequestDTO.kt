package br.com.digital.store.features.item.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemRequestDTO(
    val name: String,
    val price: Double
)
