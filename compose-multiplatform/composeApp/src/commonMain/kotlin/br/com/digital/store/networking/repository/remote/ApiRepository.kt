package br.com.digital.store.networking.repository.remote

import br.com.digital.store.common.account.SignInRequestDTO
import br.com.digital.store.common.account.TokenResponseDTO
import br.com.digital.store.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun signIn(signIn: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>>
}