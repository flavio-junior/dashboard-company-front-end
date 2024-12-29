package br.com.digital.store.features.reservation.domain

import br.com.digital.store.features.reservation.domain.status.ReservationStatus
import br.com.digital.store.features.reservation.utils.ReservationUtils.AVAILABLE
import br.com.digital.store.features.reservation.utils.ReservationUtils.RESERVED
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

fun reservationFactory(status: ReservationStatus? = null): String {
    return when(status) {
        ReservationStatus.AVAILABLE -> AVAILABLE
        ReservationStatus.RESERVED -> RESERVED
        else -> EMPTY_TEXT
    }
}
