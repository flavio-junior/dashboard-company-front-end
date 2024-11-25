package br.com.digital.store.networking.repository.remote

import br.com.digital.store.common.account.SignInRequestDTO
import br.com.digital.store.common.account.TokenResponseDTO
import br.com.digital.store.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.networking.utils.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class ApiRemoteDataSource(
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ApiRepository {

    override suspend fun signIn(
        signIn: SignInRequestDTO
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow(dispatcher) {
            httpClient.post(urlString = "api/auth/v1/signIn") {
                contentType(ContentType.Application.Json)
                setBody(signIn)
            }
        }
    }
}