package br.com.digital.store.features.fee.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponseDTO(
    val id: Long = 0,
    val author: String = "",
    val assigned: String = ""
)
