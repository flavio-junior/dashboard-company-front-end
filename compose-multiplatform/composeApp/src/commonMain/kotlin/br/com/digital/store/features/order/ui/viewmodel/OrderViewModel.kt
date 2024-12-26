package br.com.digital.store.features.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.dto.PaymentRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.features.order.data.repository.OrderRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private val _createOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createOrder: State<ObserveNetworkStateHandler<Unit>> = _createOrder

    private val _updateOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateOrder: State<ObserveNetworkStateHandler<Unit>> = _updateOrder

    private val _deleteOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteOrder: State<ObserveNetworkStateHandler<Unit>> = _deleteOrder

    private val _updateStatusDelivery =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateStatusDelivery: State<ObserveNetworkStateHandler<Unit>> = _updateStatusDelivery

    private val _deleteObject =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteObject: State<ObserveNetworkStateHandler<Unit>> = _deleteObject

    private val _closeOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val closeOrder: State<ObserveNetworkStateHandler<Unit>> = _closeOrder


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

    fun updateOrder(orderId: Long, objectId: Long, updateObject: UpdateObjectRequestDTO) {
        viewModelScope.launch {
            repository.updateOrder(
                orderId = orderId,
                objectId = objectId,
                updateObject = updateObject
            )
                .onStart {
                    _updateOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updateOrder.value = it
                }
        }
    }

    fun updateStatusDelivery(orderId: Long, status: UpdateStatusDeliveryRequestDTO) {
        viewModelScope.launch {
            repository.updateStatusDelivery(
                orderId = orderId,
                status = status
            )
                .onStart {
                    _updateStatusDelivery.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updateStatusDelivery.value = it
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

    fun closeOrder(
        orderId: Long,
        payment: PaymentRequestDTO
    ) {
        viewModelScope.launch {
            repository.closeOrder(orderId = orderId, payment = payment)
                .onStart {
                    _closeOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _closeOrder.value = it
                }
        }
    }

    fun resetOrder(reset: ResetOrder) {
        when (reset) {
            ResetOrder.UPDATE_ORDER -> {
                _updateOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.DELETE_ORDER -> {
                _deleteOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.DELETE_OBJECT -> {
                _deleteObject.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.CLOSE_ORDER -> {
                _closeOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetOrder {
    UPDATE_ORDER,
    DELETE_ORDER,
    DELETE_OBJECT,
    CLOSE_ORDER
}
