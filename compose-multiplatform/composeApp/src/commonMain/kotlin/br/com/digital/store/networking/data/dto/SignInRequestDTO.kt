package br.com.digital.store.networking.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDTO(
    val email: String,
    val password: String
)
