package br.com.digital.store.features.account.data

import br.com.digital.store.common.account.SignInRequestDTO
import br.com.digital.store.common.account.TokenResponseDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun signIn(signIn: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>>
}
