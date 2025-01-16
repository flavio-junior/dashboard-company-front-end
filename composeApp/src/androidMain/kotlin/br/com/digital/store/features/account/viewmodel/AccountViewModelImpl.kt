package br.com.digital.store.features.account.viewmodel

import br.com.digital.store.features.account.data.dto.SignInRequestDTO
import br.com.digital.store.features.account.data.dto.TokenResponseDTO

internal interface AccountViewModelImpl {
    fun signIn(signInRequestDTO: SignInRequestDTO)
    fun getToken()
    fun saveToken(token: TokenResponseDTO)
    fun refreshToken(email: String? = null, refresh: RefreshToken)
}

enum class RefreshToken {
    REFRESH_TOKEN,
    CLEAN_TOKEN
}
