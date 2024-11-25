package br.com.digital.store.common.item.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemResponseDTO(
    val id: Long,
    val name: String,
    val price: Double
)
