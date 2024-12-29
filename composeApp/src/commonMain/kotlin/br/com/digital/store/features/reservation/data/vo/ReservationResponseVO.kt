package br.com.digital.store.features.reservation.data.vo

import br.com.digital.store.features.reservation.domain.status.ReservationStatus

data class ReservationResponseVO(
    val id: Long? = 0,
    val name: String? = "",
    val status: ReservationStatus? = null
)
