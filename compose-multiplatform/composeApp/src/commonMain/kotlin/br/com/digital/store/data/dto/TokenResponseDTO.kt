package br.com.digital.store.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDTO(
    val user: String,
    val authenticated: Boolean,
    val created: String,
    val type: TypeAccount,
    val expiration: String,
    val accessToken: String,
    val refreshToken: String,
)
