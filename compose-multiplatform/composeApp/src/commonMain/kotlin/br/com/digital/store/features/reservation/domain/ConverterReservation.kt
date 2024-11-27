package br.com.digital.store.features.reservation.domain

import br.com.digital.store.common.others.dto.PageableDTO
import br.com.digital.store.common.others.vo.PageableVO
import br.com.digital.store.common.reservation.dto.ReservationResponseDTO
import br.com.digital.store.common.reservation.dto.ReservationsResponseDTO
import br.com.digital.store.common.reservation.vo.ReservationResponseVO
import br.com.digital.store.common.reservation.vo.ReservationsResponseVO

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

    private fun converterPageableDTOToVO(pageable: PageableDTO): PageableVO {
        return PageableVO(
            pageNumber = pageable.pageNumber
        )
    }
}
