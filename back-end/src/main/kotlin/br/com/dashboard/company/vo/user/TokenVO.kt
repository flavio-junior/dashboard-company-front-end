package br.com.dashboard.company.vo.user

import br.com.dashboard.company.utils.common.TypeAccount
import java.time.LocalDateTime

data class TokenVO(
    val user: String? = null,
    val authenticated: Boolean? = null,
    val created: LocalDateTime? = null,
    val type: TypeAccount? = null,
    val expiration: LocalDateTime? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
