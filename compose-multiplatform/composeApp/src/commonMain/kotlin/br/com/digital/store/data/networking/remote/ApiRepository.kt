package br.com.digital.store.data.networking.remote

import br.com.digital.store.data.dto.SignInRequestDTO
import br.com.digital.store.data.dto.TokenResponseDTO
import br.com.digital.store.utils.NetworkError
import br.com.digital.store.utils.Result

interface ApiRepository {
    suspend fun signIn(signIn: SignInRequestDTO): Result<TokenResponseDTO, NetworkError>
}