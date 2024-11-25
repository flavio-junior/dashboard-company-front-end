package br.com.digital.store.common.reservation.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReservationResponseDTO(
    val id: Long,
    val name: String
)
