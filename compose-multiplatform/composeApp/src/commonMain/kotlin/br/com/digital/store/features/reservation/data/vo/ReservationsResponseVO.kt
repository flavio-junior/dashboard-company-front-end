package br.com.digital.store.features.reservation.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class ReservationsResponseVO(
    val totalPages: Int,
    val content: List<ReservationResponseVO>,
    val pageable: PageableVO
)
