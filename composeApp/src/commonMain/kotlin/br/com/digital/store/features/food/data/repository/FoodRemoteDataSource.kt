package br.com.digital.store.features.food.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.food.data.dto.FoodRequestDTO
import br.com.digital.store.features.food.data.dto.FoodResponseDTO
import br.com.digital.store.features.food.data.dto.FoodsResponseDTO
import br.com.digital.store.features.food.data.dto.UpdateFoodRequestDTO
import br.com.digital.store.features.food.data.dto.UpdatePriceFoodRequestDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class FoodRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : FoodRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllFoods(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<FoodsResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/digital/order/foods/v1")
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

    override fun findFoodByName(name: String): Flow<ObserveNetworkStateHandler<List<FoodResponseDTO>>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/digital/order/foods/v1/find/food/by/$name")
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewFood(food: FoodRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/digital/order/foods/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = food)
            }
        }
    }

    override fun updateFood(food: UpdateFoodRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/digital/order/foods/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = food)
            }
        }
    }

    override fun updatePriceFood(
        id: Long,
        price: UpdatePriceFoodRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.patch {
                url(urlString = "/api/digital/order/foods/v1/update/price/food/$id")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = price)
            }
        }
    }

    override fun deleteFood(id: Long): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/digital/order/foods/v1/$id")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
