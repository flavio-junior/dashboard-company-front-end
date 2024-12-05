package br.com.digital.store.features.reservation.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReservationResponseDTO(
    val id: Long? = 0,
    val name: String? = ""
)
