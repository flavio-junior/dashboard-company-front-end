package br.com.digital.store.features.reservation.domain

import br.com.digital.store.features.others.converterPageableDTOToVO
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.features.reservation.data.dto.ReservationsResponseDTO
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO
import br.com.digital.store.features.reservation.data.vo.ReservationsResponseVO

class ConverterReservation {

    fun converterContentDTOToVO(content: ReservationsResponseDTO): ReservationsResponseVO {
        return ReservationsResponseVO(
            totalPages = content.totalPages,
            content = converterReservationsResponseDTOToVO(reservations = content.content),
            converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterReservationsResponseDTOToVO(
        reservations: List<ReservationResponseDTO>
    ): List<ReservationResponseVO> {
        return reservations.map {
            ReservationResponseVO(
                id = it.id,
                name = it.name
            )
        }
    }
}
