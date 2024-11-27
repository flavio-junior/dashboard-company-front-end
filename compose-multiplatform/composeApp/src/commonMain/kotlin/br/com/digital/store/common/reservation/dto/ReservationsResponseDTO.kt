package br.com.digital.store.common.reservation.dto

import br.com.digital.store.common.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ReservationsResponseDTO(
    val totalPages: Int,
    val content: List<ReservationResponseDTO>,
    val pageable: PageableDTO
)
