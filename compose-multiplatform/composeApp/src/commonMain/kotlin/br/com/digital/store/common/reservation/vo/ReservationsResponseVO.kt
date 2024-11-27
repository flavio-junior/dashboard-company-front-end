package br.com.digital.store.common.reservation.vo

import br.com.digital.store.common.others.vo.PageableVO

data class ReservationsResponseVO(
    val totalPages: Int,
    val content: List<ReservationResponseVO>,
    val pageable: PageableVO
)
