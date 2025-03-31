package br.com.digital.store.features.report.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import br.com.digital.store.features.report.data.dto.ReportRequestDTO
import br.com.digital.store.features.report.data.dto.ReportsResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class ReportDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : ReportRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllReports(
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ReportsResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/digital/order/report/v1/find/all")
                    parameters.append(name = "page", value = page.toString())
                    parameters.append(name = "size", value = size.toString())
                    parameters.append(name = "sort", value = sort)
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewReport(
        report: ReportRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/digital/order/report/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = report)
            }
        }
    }

    override fun deleteReport(
        reportId: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/digital/order/report/v1/$reportId")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
