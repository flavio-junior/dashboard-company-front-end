package br.com.digital.store.data.networking.remote

import br.com.digital.store.data.dto.SignInRequestDTO
import br.com.digital.store.data.dto.TokenResponseDTO
import br.com.digital.store.utils.NetworkError
import br.com.digital.store.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class APIImpl(
    private val httpClient: HttpClient
) : ApiRepository {

    override suspend fun signIn(signIn: SignInRequestDTO): Result<TokenResponseDTO, NetworkError> {
        val response = try {
            httpClient.post(urlString = "http://192.168.1.105:8001/api/auth/v1/signIn") {
                contentType(ContentType.Application.Json)
                setBody(signIn)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            in 200..299 -> {
                Result.Success(response.body<TokenResponseDTO>())
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}