package br.com.digital.store.features.employee.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.employee.data.dto.EmployeeNameRequestDTO
import br.com.digital.store.features.employee.data.dto.EmployeeResponseDTO
import br.com.digital.store.features.employee.data.repository.EmployeeRepository
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val repository: EmployeeRepository
) : ViewModel() {

    private val _findEmployeeByName =
        mutableStateOf<ObserveNetworkStateHandler<List<EmployeeResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findEmployeeByName: State<ObserveNetworkStateHandler<List<EmployeeResponseDTO>>> =
        _findEmployeeByName

    fun findEmployeeByName(name: String) {
        viewModelScope.launch {
            repository.findEmployeeByName(name = EmployeeNameRequestDTO(name = name))
                .onStart {
                    _findEmployeeByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findEmployeeByName.value = it
                }
        }
    }

    fun resetEmployee() {
        _findEmployeeByName.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
