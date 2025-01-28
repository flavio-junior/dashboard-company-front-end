package br.com.digital.store.features.fee.domain.converter

import br.com.digital.store.features.fee.data.dto.DayResponseDTO
import br.com.digital.store.features.fee.data.dto.FeeResponseDTO
import br.com.digital.store.features.fee.data.vo.DayResponseVO
import br.com.digital.store.features.fee.data.vo.FeeResponseVO

class ConverterFee {

    fun converterContentDTOToVO(content: List<FeeResponseDTO>): List<FeeResponseVO> {
        return content.map {
            FeeResponseVO(
                id = it.id,
                assigned = it.assigned,
                price = it.price,
                days = convertDayOfWeek(days = it.days ?: emptyList())
            )
        }
    }

    private fun convertDayOfWeek(days: List<DayResponseDTO>): List<DayResponseVO> {
        return days.map {
            DayResponseVO(
                id = it.id,
                day = it.day
            )
        }
    }
}
