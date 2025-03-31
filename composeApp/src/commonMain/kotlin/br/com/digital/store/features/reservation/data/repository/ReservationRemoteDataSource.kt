package br.com.digital.store.features.reservation.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import br.com.digital.store.features.reservation.data.dto.EditReservationRequestDTO
import br.com.digital.store.features.reservation.data.dto.GenerateReservationsRequestVO
import br.com.digital.store.features.reservation.data.dto.ReservationRequestDTO
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.features.reservation.data.dto.ReservationsResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class ReservationRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : ReservationRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllReservations(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ReservationsResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/digital/order/reservations/v1")
                    parameters.append("name", name)
                    parameters.append("page", page.toString())
                    parameters.append("size", size.toString())
                    parameters.append("sort", sort)
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun finReservationByName(
        name: String
    ): Flow<ObserveNetworkStateHandler<List<ReservationResponseDTO>>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/digital/order/reservations/v1/find/reservation/by/$name")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewReservation(reservation: ReservationRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/digital/order/reservations/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(reservation)
            }
        }
    }

    override fun generateReservations(
        body: GenerateReservationsRequestVO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/digital/order/reservations/v1/generate/reservations")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = body)
            }
        }
    }

    override fun editReservation(reservation: EditReservationRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/digital/order/reservations/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(reservation)
            }
        }
    }

    override fun deleteReservation(id: Long): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/digital/order/reservations/v1/$id")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
