package br.com.digital.store.features.item.data

import br.com.digital.store.common.item.dto.EditItemRequestDTO
import br.com.digital.store.common.item.dto.ItemRequestDTO
import br.com.digital.store.common.item.dto.ItemsResponseDTO
import br.com.digital.store.features.account.data.LocalStorageImp
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.toResultFlow
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

class ItemRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : ItemRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllItems(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ItemsResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/dashboard/company/items/v1")
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

    override fun createNewItem(item: ItemRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/dashboard/company/items/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(item)
            }
        }
    }

    override fun editItem(item: EditItemRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/dashboard/company/items/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(item)
            }
        }
    }

    override fun deleteItem(id: Long): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/dashboard/company/items/v1/$id")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
