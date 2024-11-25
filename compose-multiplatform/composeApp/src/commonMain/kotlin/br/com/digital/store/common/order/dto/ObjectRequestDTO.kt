package br.com.digital.store.common.order.dto

import kotlinx.serialization.Serializable

@Serializable
data class ObjectRequestDTO(
    val identifier: Int,
    val quantity: Int,
    val type: String
)
