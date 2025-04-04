package br.com.digital.store.features.payment.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import br.com.digital.store.features.payment.data.dto.PaymentsResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class PaymentRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : PaymentRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllPayments(
        code: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<PaymentsResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/digital/order/payment/v1/details/all")
                    parameters.append(name = "code", value = code)
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
}
