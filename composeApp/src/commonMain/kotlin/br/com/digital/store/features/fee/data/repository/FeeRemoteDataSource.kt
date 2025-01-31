package br.com.digital.store.features.fee.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.fee.data.dto.CreateFeeRequestDTO
import br.com.digital.store.features.fee.data.dto.DayRequestDTO
import br.com.digital.store.features.fee.data.dto.FeeResponseDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class FeeRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : FeeRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllFees(): Flow<ObserveNetworkStateHandler<List<FeeResponseDTO>>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/dashboard/company/fees/v1")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewFee(
        fee: CreateFeeRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url {
                    path("/api/dashboard/company/fees/v1")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = fee)
            }
        }
    }

    override fun addDaysFee(
        id: Long,
        daysOfWeek: DayRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url {
                    path("/api/dashboard/company/fees/v1/add/days/fee/$id")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = daysOfWeek)
            }
        }
    }

    override fun deleteDayFee(
        feeId: Long,
        dayId: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url {
                    path("/api/dashboard/company/fees/v1/fee/$feeId/$dayId")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
