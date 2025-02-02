package br.com.digital.store.features.fee.data.vo

import br.com.digital.store.features.fee.domain.type.Function

data class FeeResponseVO(
    val id: Long = 0,
    var assigned: Function? = null,
    val percentage: Int = 0,
    var days: List<DayResponseVO>? = null
)
