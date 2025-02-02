package br.com.digital.store.features.fee.data.dto

import br.com.digital.store.features.fee.domain.type.Function
import kotlinx.serialization.Serializable

@Serializable
data class FeeResponseDTO(
    val id: Long = 0,
    var assigned: Function? = null,
    val percentage: Int = 0,
    var days: List<DayResponseDTO>? = null
)
