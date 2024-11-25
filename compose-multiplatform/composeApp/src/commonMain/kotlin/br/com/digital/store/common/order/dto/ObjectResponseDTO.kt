package br.com.digital.store.common.order.dto

import kotlinx.serialization.Serializable

@Serializable
data class ObjectResponseDTO(
    val id: Int,
    val identifier: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val type: String
)
