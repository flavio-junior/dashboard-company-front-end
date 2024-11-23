package br.com.digital.store.networking.data.repository.remote

import br.com.digital.store.networking.data.dto.SignInRequestDTO
import br.com.digital.store.networking.data.dto.TokenResponseDTO
import br.com.digital.store.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun signIn(signIn: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>>
}