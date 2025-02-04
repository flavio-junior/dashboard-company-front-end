package br.com.digital.store.features.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.networking.resources.DescriptionError
import br.com.digital.store.features.networking.resources.ErrorType
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.dto.PaymentRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.features.order.data.repository.OrderRepository
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.converter.ConverterOrder
import br.com.digital.store.features.order.domain.others.Action
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository,
    private val converter: ConverterOrder
) : ViewModel() {

    private val _createOrder =
        mutableStateOf<ObserveNetworkStateHandler<OrderResponseVO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val createOrder: State<ObserveNetworkStateHandler<OrderResponseVO>> = _createOrder

    private val _deleteOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteOrder: State<ObserveNetworkStateHandler<Unit>> = _deleteOrder

    private val _updateStatusDelivery =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateStatusDelivery: State<ObserveNetworkStateHandler<Unit>> = _updateStatusDelivery

    private val _closeOrder =
        mutableStateOf<ObserveNetworkStateHandler<OrderResponseVO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val closeOrder: State<ObserveNetworkStateHandler<OrderResponseVO>> = _closeOrder

    private val _updateStatusOverview =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateStatusOverview: State<ObserveNetworkStateHandler<Unit>> = _updateStatusOverview

    private val _incrementOverview =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementOverview: State<ObserveNetworkStateHandler<Unit>> = _incrementOverview

    private val _removeOverview =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeOverview: State<ObserveNetworkStateHandler<Unit>> = _removeOverview

    private val _removeObject =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeObject: State<ObserveNetworkStateHandler<Unit>> = _removeObject

    private val _incrementMoreObjectsOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementMoreObjectsOrder: State<ObserveNetworkStateHandler<Unit>> =
        _incrementMoreObjectsOrder

    private val _incrementMoreReservationsOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementMoreReservationsOrder: State<ObserveNetworkStateHandler<Unit>> =
        _incrementMoreReservationsOrder

    private val _removeReservationOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeReservationOrder: State<ObserveNetworkStateHandler<Unit>> =
        _removeReservationOrder

    fun createOrder(order: OrderRequestDTO) {
        CoroutineScope(context = Dispatchers.IO).launch {
            try {
                repository.createNewOrder(order = order)
                    .collect { response ->
                        when (response) {
                            is ObserveNetworkStateHandler.Loading -> {
                                _createOrder.value =
                                    ObserveNetworkStateHandler.Loading(l = response.l)
                            }

                            is ObserveNetworkStateHandler.Error -> {
                                _createOrder.value =
                                    ObserveNetworkStateHandler.Error(e = response.exception)
                            }

                            is ObserveNetworkStateHandler.Success -> {
                                response.result?.let { result ->
                                    val objectConverted =
                                        converter.converterOrderResponseDTOToVO(order = result)
                                    _createOrder.value =
                                        ObserveNetworkStateHandler.Success(s = objectConverted)
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
                _createOrder.value = ObserveNetworkStateHandler.Error(
                    e = DescriptionError(
                        type = ErrorType.CLIENT,
                        message = e.message
                    )
                )
            }
        }
    }

    fun updateOrder(orderId: Long, objectId: Long, updateObject: UpdateObjectRequestDTO) {
        viewModelScope.launch {
            when (updateObject.action) {
                Action.UPDATE_STATUS_OVERVIEW -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _updateStatusOverview.value =
                                ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _updateStatusOverview.value = it
                        }
                }

                Action.INCREMENT_OVERVIEW -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _incrementOverview.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _incrementOverview.value = it
                        }
                }

                Action.REMOVE_OVERVIEW -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _removeOverview.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _removeOverview.value = it
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

    fun incrementMoreReservationsOrder(
        orderId: Long,
        reservationsToSava: List<ReservationResponseDTO>
    ) {
        viewModelScope.launch {
            repository.incrementMoreReservationsOrder(
                orderId = orderId,
                reservationsToSava = reservationsToSava
            )
                .onStart {
                    _incrementMoreReservationsOrder.value =
                        ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _incrementMoreReservationsOrder.value = it
                }
        }
    }

    fun removeReservationOrder(
        orderId: Long,
        reservationId: Long
    ) {
        viewModelScope.launch {
            repository.removeReservationOrder(
                orderId = orderId,
                reservationId = reservationId
            )
                .onStart {
                    _removeReservationOrder.value =
                        ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _removeReservationOrder.value = it
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
        force: Boolean = false,
        payment: PaymentRequestDTO
    ) {
        CoroutineScope(context = Dispatchers.IO).launch {
            try {
                repository.closeOrder(orderId = orderId, force = force, payment = payment)
                    .collect { response ->
                        when (response) {
                            is ObserveNetworkStateHandler.Loading -> {
                                _closeOrder.value =
                                    ObserveNetworkStateHandler.Loading(l = response.l)
                            }

                            is ObserveNetworkStateHandler.Error -> {
                                _closeOrder.value =
                                    ObserveNetworkStateHandler.Error(e = response.exception)
                            }

                            is ObserveNetworkStateHandler.Success -> {
                                response.result?.let { result ->
                                    val objectConverted =
                                        converter.converterOrderResponseDTOToVO(order = result)
                                    _closeOrder.value =
                                        ObserveNetworkStateHandler.Success(s = objectConverted)
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
                _closeOrder.value = ObserveNetworkStateHandler.Error(
                    e = DescriptionError(
                        type = ErrorType.CLIENT,
                        message = e.message
                    )
                )
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

            ResetOrder.UPDATE_STATUS_OVERVIEW -> {
                _updateStatusOverview.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.INCREMENT_OVERVIEW -> {
                _incrementOverview.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.REMOVE_OVERVIEW -> {
                _removeOverview.value = ObserveNetworkStateHandler.Loading(l = false)
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
                _incrementMoreObjectsOrder.value =
                    ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.INCREMENT_MORE_RESERVATIONS_ORDER -> {
                _incrementMoreReservationsOrder.value =
                    ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.REMOVE_RESERVATION -> {
                _removeReservationOrder.value =
                    ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetOrder {
    CREATE_ORDER,
    DELETE_ORDER,
    CLOSE_ORDER,
    UPDATE_STATUS_DELIVERY,
    UPDATE_STATUS_OVERVIEW,
    INCREMENT_OVERVIEW,
    REMOVE_OVERVIEW,
    REMOVE_OBJECT,
    INCREMENT_MORE_RESERVATIONS_ORDER,
    REMOVE_RESERVATION,
    INCREMENT_MORE_OBJECTS_ORDER
}
