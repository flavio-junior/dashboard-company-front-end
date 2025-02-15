package br.com.digital.store.features.settings.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import br.com.digital.store.features.settings.data.dto.VersionResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class SettingsRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : SettingsRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun checkUpdates(
        version: String
    ): Flow<ObserveNetworkStateHandler<VersionResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/settings/v1/check/updates/$version")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
