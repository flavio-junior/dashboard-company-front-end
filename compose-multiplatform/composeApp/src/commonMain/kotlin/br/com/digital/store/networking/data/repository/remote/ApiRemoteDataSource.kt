package br.com.digital.store.networking.data.repository.remote

import br.com.digital.store.networking.data.dto.SignInRequestDTO
import br.com.digital.store.networking.data.dto.TokenResponseDTO
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

    override suspend fun signIn(signIn: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow(dispatcher) { // Usa toResultFlow com dispatcher
            httpClient.post("http://192.168.1.105:8001/api/auth/v1/signIn") {
                contentType(ContentType.Application.Json)
                setBody(signIn)
            }
        }
    }
}