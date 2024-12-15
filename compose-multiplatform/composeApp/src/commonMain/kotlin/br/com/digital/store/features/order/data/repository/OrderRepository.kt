package br.com.digital.store.features.order.data.repository

import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.dto.OrdersResponseDTO
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun findAllOpenOrders(
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<OrdersResponseDTO>>

    fun findAllClosedOrders(
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<OrdersResponseDTO>>

    fun createNewOrder(order: OrderRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updateOrder(
        orderId: Long,
        objectId: Long,
        updateObject: UpdateObjectRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>>

    fun updateStatusDelivery(
        orderId: Long,
        status: UpdateStatusDeliveryRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>>

    fun deleteOrder(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
