package br.com.digital.store.features.account.data.repository

import br.com.digital.store.features.account.data.dto.SignInRequestDTO
import br.com.digital.store.features.account.data.dto.TokenResponseDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.put
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

    override suspend fun refreshToken(
        email: String
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow {
            httpClient.put(urlString = "/api/auth/v1/refresh/$email") {
                setBody(email)
            }
        }
    }
}