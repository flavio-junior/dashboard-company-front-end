package br.com.digital.store.features.employee.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.employee.data.dto.EmployeeNameRequestDTO
import br.com.digital.store.features.employee.data.dto.EmployeeResponseDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class EmployeeRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : EmployeeRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findEmployeeByName(
        name: EmployeeNameRequestDTO
    ): Flow<ObserveNetworkStateHandler<List<EmployeeResponseDTO>>> {
        return toResultFlow {
            httpClient.get {
                url(urlString = "/api/dashboard/company/employees/v1/find/employee/by/${name.name}")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
