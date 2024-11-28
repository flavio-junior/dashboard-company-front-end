package br.com.digital.store.features.reservation.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReservationRequestDTO(
    val name: String
)
