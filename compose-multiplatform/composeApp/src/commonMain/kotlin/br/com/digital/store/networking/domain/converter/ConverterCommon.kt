package br.com.digital.store.networking.domain.converter

import br.com.digital.store.common.account.TokenResponseDTO
import br.com.digital.store.common.account.TokenResponseVO

class ConverterCommon {

    fun converterTokenRequestDTOToTokenResponseVO(token: TokenResponseDTO): TokenResponseVO {
        return TokenResponseVO(
            user = token.user,
            authenticated = token.authenticated,
            created = token.created,
            type = token.type.name,
            expiration = token.expiration,
            accessToken = token.accessToken,
            refreshToken = token.refreshToken
        )
    }
}