package br.com.digital.store.features.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.repository.OrderRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val repository: OrderRepository
): ViewModel() {

    private val _createOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createOrder: State<ObserveNetworkStateHandler<Unit>> = _createOrder

    private val _deleteOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteOrder: State<ObserveNetworkStateHandler<Unit>> = _deleteOrder

    fun createOrder(order: OrderRequestDTO) {
        viewModelScope.launch {
            repository.createNewOrder(order = order)
                .onStart {
                    _createOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createOrder.value = it
                }
        }
    }

    fun deleteOrder(id: Long) {
        viewModelScope.launch {
            repository.deleteOrder(id = id)
                .onStart {
                    _deleteOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteOrder.value = it
                }
        }
    }
}
