package br.com.digital.store.features.fee.data.dto

import br.com.digital.store.features.fee.domain.day.DayOfWeek
import kotlinx.serialization.Serializable

@Serializable
data class DayResponseDTO(
    var id: Long = 0,
    var day: DayOfWeek? = null
)
