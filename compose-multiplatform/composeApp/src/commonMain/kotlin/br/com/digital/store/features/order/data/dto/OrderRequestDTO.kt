package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.data.others.TypeOrder
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDTO(
    val type: TypeOrder? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    val address: AddressRequestDTO? = null,
    val objects: List<ObjectRequestDTO>,
    val payment: PaymentRequestDTO
)
