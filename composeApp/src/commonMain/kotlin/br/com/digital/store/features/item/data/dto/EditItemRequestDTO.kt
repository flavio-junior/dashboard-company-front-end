package br.com.digital.store.features.item.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditItemRequestDTO(
    val id: Long = 0,
    val name: String = "",
    val price: Double = 0.0
)
