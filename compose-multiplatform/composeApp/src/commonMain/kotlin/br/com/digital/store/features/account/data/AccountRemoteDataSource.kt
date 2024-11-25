package br.com.digital.store.features.account.data

import br.com.digital.store.common.account.SignInRequestDTO
import br.com.digital.store.common.account.TokenResponseDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class AccountRemoteDataSource(
    private val httpClient: HttpClient
) : AccountRepository {

    override suspend fun signIn(
        signIn: SignInRequestDTO
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow {
            httpClient.post(urlString = "api/auth/v1/signIn") {
                setBody(signIn)
            }
        }
    }
}