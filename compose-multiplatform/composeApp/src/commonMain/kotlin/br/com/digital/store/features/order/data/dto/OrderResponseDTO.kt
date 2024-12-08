package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.status.OrderStatus
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDTO(
    val id: Long? = 0,
    val createdAt: String? = "",
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    val address: AddressResponseDTO? = null,
    val objects: List<ObjectResponseDTO>? = null,
    val quantity: Int = 0,
    val price: Double = 0.0,
    val payment: PaymentResponseDTO? = null
)
