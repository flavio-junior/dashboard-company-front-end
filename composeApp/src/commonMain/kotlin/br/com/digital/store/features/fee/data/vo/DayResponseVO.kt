package br.com.digital.store.features.fee.data.vo

import br.com.digital.store.features.fee.domain.day.DayOfWeek

data class DayResponseVO(
    var id: Long = 0,
    var day: DayOfWeek? = null
)
