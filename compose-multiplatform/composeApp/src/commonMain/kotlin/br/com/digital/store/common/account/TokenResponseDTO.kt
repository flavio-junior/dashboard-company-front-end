package br.com.digital.store.common.account

import br.com.digital.store.features.account.domain.type.TypeAccount
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
