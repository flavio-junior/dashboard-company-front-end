package br.com.digital.store.features.order.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ObjectRequestDTO(
    val identifier: Int,
    val quantity: Int,
    val type: String
)
