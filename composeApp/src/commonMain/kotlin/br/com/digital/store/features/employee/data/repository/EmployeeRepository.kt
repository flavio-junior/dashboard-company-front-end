package br.com.digital.store.features.employee.data.repository

import br.com.digital.store.features.employee.data.dto.EmployeeNameRequestDTO
import br.com.digital.store.features.employee.data.dto.EmployeeResponseDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun findEmployeeByName(
        name: EmployeeNameRequestDTO
    ): Flow<ObserveNetworkStateHandler<List<EmployeeResponseDTO>>>
}
