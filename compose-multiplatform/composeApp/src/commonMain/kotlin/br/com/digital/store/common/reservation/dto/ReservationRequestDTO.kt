package br.com.digital.store.common.reservation.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReservationRequestDTO(
    val name: String
)
