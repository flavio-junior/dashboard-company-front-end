package br.com.digital.store.features.reservation.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ReservationsResponseDTO(
    val totalPages: Int,
    val content: List<ReservationResponseDTO>,
    val pageable: PageableDTO
)
