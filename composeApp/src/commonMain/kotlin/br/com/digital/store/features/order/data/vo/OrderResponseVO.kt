package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.status.OrderStatus
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO

data class OrderResponseVO(
    val id: Long = 0,
    val createdAt: String = "",
    var qrCode: String? = "",
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseVO>? = null,
    val address: AddressResponseVO? = null,
    val objects: List<ObjectResponseVO>? = null,
    val quantity: Int = 0,
    val total: Double = 0.0,
    val payment: PaymentResponseVO? = null
)
