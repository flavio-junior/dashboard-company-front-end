package br.com.digital.store.common.item.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemRequestDTO(
    val name: String,
    val price: Double
)
