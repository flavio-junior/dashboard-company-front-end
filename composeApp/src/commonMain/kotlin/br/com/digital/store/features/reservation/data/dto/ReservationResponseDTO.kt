package br.com.digital.store.features.reservation.data.dto

import br.com.digital.store.features.reservation.domain.status.ReservationStatus
import kotlinx.serialization.Serializable

@Serializable
data class ReservationResponseDTO(
    val id: Long = 0,
    val name: String = "",
    val status: ReservationStatus? = null
)
