package br.com.digital.store.common.order.dto

import br.com.digital.store.common.order.others.TypeOrder
import br.com.digital.store.common.reservation.dto.ReservationResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDTO(
    val type: TypeOrder? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    val address: AddressRequestDTO? = null,
    val objects: List<ObjectRequestDTO>,
    val payment: PaymentRequestDTO
)
