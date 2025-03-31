package br.com.digital.store.features.resume.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import br.com.digital.store.features.resume.data.dto.AnaliseDayDTO
import br.com.digital.store.features.resume.data.dto.TypeAnalysisRequestDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class ResumeRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : ResumeRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun getDetailsOfAnalysis(
        startDate: String?,
        endDate: String?,
        type: TypeAnalysisRequestDTO
    ): Flow<ObserveNetworkStateHandler<AnaliseDayDTO>> {
        return toResultFlow {
            httpClient.post {
                url {
                    path("/api/digital/order/payment/v1/details/analysis")
                    if (startDate != null) {
                        parameters.append(name = "date", value = startDate)
                    }
                    if (startDate != null && endDate != null) {
                        parameters.append(
                            name = "start",
                            value = startDate
                        )
                        parameters.append(
                            name = "end",
                            value = endDate
                        )
                    }
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = type)
            }
        }
    }
}
