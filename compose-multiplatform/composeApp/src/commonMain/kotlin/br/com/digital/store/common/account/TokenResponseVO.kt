package br.com.digital.store.common.account

data class TokenResponseVO(
    val user: String = "",
    val authenticated: Boolean = false,
    val created: String = "",
    val type: String = "",
    val expiration: String = "",
    val accessToken: String = "",
    val refreshToken: String = ""
)
