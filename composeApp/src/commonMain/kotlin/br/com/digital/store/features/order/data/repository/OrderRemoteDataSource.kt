package br.com.digital.store.features.order.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import br.com.digital.store.features.order.data.dto.AddressRequestDTO
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.dto.OrderResponseDTO
import br.com.digital.store.features.order.data.dto.OrdersResponseDTO
import br.com.digital.store.features.order.data.dto.PaymentRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
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

class OrderRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : OrderRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllOpenOrders(
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<OrdersResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/dashboard/company/orders/v1/open")
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

    override fun createNewOrder(
        order: OrderRequestDTO
    ): Flow<ObserveNetworkStateHandler<OrderResponseDTO>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/dashboard/company/orders/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = order)
            }
        }
    }

    override fun updateOrder(
        orderId: Long,
        objectId: Long,
        updateObject: UpdateObjectRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/dashboard/company/orders/v1/$orderId/update/object/$objectId")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = updateObject)
            }
        }
    }

    override fun updateAddressOrder(
        orderId: Long,
        addressId: Long,
        updateAddress: AddressRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/dashboard/company/orders/v1/$orderId/update/address/$addressId")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = updateAddress)
            }
        }
    }

    override fun updateStatusDelivery(
        orderId: Long,
        status: UpdateStatusDeliveryRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/dashboard/company/orders/v1/$orderId/update/status/delivery")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = status)
            }
        }
    }

    override fun incrementMoreObjectsOrder(
        orderId: Long,
        incrementObjects: List<ObjectRequestDTO>
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/dashboard/company/orders/v1/${orderId}/increment/more/objects/order")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = incrementObjects)
            }
        }
    }

    override fun incrementMoreReservationsOrder(
        orderId: Long,
        reservationsToSava: List<ReservationResponseDTO>
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/dashboard/company/orders/v1/${orderId}/increment/more/reservations/order")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = reservationsToSava)
            }
        }
    }

    override fun removeReservationOrder(
        orderId: Long,
        reservationId: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/dashboard/company/orders/v1/${orderId}/remove/reservation/${reservationId}")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun deleteOrder(id: Long): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/dashboard/company/orders/v1/$id")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun closeOrder(
        orderId: Long,
        payment: PaymentRequestDTO
    ): Flow<ObserveNetworkStateHandler<OrderResponseDTO>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "api/dashboard/company/orders/v1/payment/$orderId")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = payment)
            }
        }
    }
}
