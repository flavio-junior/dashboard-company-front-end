package br.com.dashboard.company.vo.user

import br.com.dashboard.company.utils.common.TypeAccount
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
