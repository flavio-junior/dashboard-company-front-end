package br.com.digital.store.features.reservation.data

import br.com.digital.store.common.reservation.dto.EditReservationRequestDTO
import br.com.digital.store.common.reservation.dto.ReservationRequestDTO
import br.com.digital.store.common.reservation.dto.ReservationsResponseDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun findAllReservations(
        name: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ReservationsResponseDTO>>
    fun createNewReservation(reservation: ReservationRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun editReservation(reservation: EditReservationRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteReservation(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
