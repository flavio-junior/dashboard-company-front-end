package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.fee.data.dto.FeeResponseOrderDTO
import br.com.digital.store.features.order.domain.status.OrderStatus
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDTO(
    val id: Long = 0,
    @SerialName(value = "created_at")
    val createdAt: String = "",
    @SerialName(value = "qr_code")
    var qrCode: String? = "",
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    var fee: FeeResponseOrderDTO? = null,
    val address: AddressResponseDTO? = null,
    val objects: List<ObjectResponseDTO>? = null,
    val quantity: Int = 0,
    val total: Double = 0.0,
    val payment: PaymentResponseDTO? = null
)
