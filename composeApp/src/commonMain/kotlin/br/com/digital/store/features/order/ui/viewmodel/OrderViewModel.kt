package br.com.digital.store.features.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.dto.PaymentRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.features.order.data.repository.OrderRepository
import br.com.digital.store.features.order.domain.others.Action
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private val _createOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createOrder: State<ObserveNetworkStateHandler<Unit>> = _createOrder

    private val _deleteOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteOrder: State<ObserveNetworkStateHandler<Unit>> = _deleteOrder

    private val _updateStatusDelivery =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateStatusDelivery: State<ObserveNetworkStateHandler<Unit>> = _updateStatusDelivery

    private val _closeOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val closeOrder: State<ObserveNetworkStateHandler<Unit>> = _closeOrder

    private val _updateStatus =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateStatus: State<ObserveNetworkStateHandler<Unit>> = _updateStatus

    private val _increment =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val increment: State<ObserveNetworkStateHandler<Unit>> = _increment

    private val _decrement =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val decrement: State<ObserveNetworkStateHandler<Unit>> = _decrement

    private val _removeObject =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeObject: State<ObserveNetworkStateHandler<Unit>> = _removeObject

    private val _incrementMoreObjectsOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementMoreObjectsOrder: State<ObserveNetworkStateHandler<Unit>> =
        _incrementMoreObjectsOrder

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
            when (updateObject.action) {
                Action.UPDATE_STATUS -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _updateStatus.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _updateStatus.value = it
                        }
                }

                Action.INCREMENT -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _increment.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _increment.value = it
                        }
                }

                Action.DECREMENT -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _decrement.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _decrement.value = it
                        }
                }

                Action.REMOVE_OBJECT -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _removeObject.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _removeObject.value = it
                        }
                }
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

    fun incrementMoreObjectsOrder(
        orderId: Long,
        incrementObjects: List<ObjectRequestDTO>
    ) {
        viewModelScope.launch {
            repository.incrementMoreObjectsOrder(
                orderId = orderId,
                incrementObjects = incrementObjects
            )
                .onStart {
                    _incrementMoreObjectsOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _incrementMoreObjectsOrder.value = it
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
            ResetOrder.CREATE_ORDER -> {
                _createOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.UPDATE_STATUS_DELIVERY -> {
                _updateStatusDelivery.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.UPDATE_STATUS_OBJECT -> {
                _updateStatus.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.INCREMENT -> {
                _increment.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.DECREMENT -> {
                _decrement.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.REMOVE_OBJECT -> {
                _removeObject.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.DELETE_ORDER -> {
                _deleteOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.CLOSE_ORDER -> {
                _closeOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.INCREMENT_MORE_OBJECTS_ORDER -> {
                _incrementMoreObjectsOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetOrder {
    CREATE_ORDER,
    DELETE_ORDER,
    CLOSE_ORDER,
    UPDATE_STATUS_DELIVERY,
    UPDATE_STATUS_OBJECT,
    INCREMENT,
    DECREMENT,
    REMOVE_OBJECT,
    INCREMENT_MORE_OBJECTS_ORDER
}
