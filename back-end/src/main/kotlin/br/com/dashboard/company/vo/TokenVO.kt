package br.com.dashboard.company.vo

import java.util.*

data class TokenVO(
    val user: String? = null,
    val authenticated: Boolean? = null,
    val created: Date? = null,
    val type: TypeAccount? = null,
    val expiration: Date? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
