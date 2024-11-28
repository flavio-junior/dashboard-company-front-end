package br.com.digital.store.features.account.data.repository

import br.com.digital.store.features.account.data.dto.SignInRequestDTO
import br.com.digital.store.features.account.data.dto.TokenResponseDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun signIn(signIn: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>>
}
