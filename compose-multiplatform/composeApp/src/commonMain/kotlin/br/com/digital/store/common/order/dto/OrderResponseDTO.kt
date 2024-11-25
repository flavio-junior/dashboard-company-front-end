package br.com.digital.store.common.order.dto

import br.com.digital.store.common.order.others.OrderStatus
import br.com.digital.store.common.order.others.TypeOrder
import br.com.digital.store.common.reservation.dto.ReservationResponseDTO

data class OrderResponseVO(
    val id: Long,
    val createdAt: String,
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    val address: AddressResponseDTO? = null,
    val objects: MutableList<ObjectResponseDTO>? = null,
    val quantity: Int = 0,
    val price: Double = 0.0,
    val payment: PaymentResponseDTO? = null
)
