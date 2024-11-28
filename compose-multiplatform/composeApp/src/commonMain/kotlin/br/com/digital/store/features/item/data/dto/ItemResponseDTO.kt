package br.com.digital.store.features.item.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemResponseDTO(
    val id: Long,
    val name: String,
    val price: Double
)
