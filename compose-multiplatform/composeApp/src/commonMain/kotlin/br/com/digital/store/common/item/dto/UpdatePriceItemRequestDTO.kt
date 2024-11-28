package br.com.digital.store.common.item.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePriceItemRequestDTO(
    val id: Long = 0,
    val name: String = "",
    val price: Double = 0.0
)
