package br.com.digital.store.features.fee.domain.factory

import br.com.digital.store.components.strings.StringsUtils.ALL
import br.com.digital.store.components.strings.StringsUtils.ATTENDANT
import br.com.digital.store.components.strings.StringsUtils.BOX
import br.com.digital.store.components.strings.StringsUtils.FRIDAY
import br.com.digital.store.components.strings.StringsUtils.MONDAY
import br.com.digital.store.components.strings.StringsUtils.SATURDAY
import br.com.digital.store.components.strings.StringsUtils.SUNDAY
import br.com.digital.store.components.strings.StringsUtils.THURSDAY
import br.com.digital.store.components.strings.StringsUtils.TUESDAY
import br.com.digital.store.components.strings.StringsUtils.WAITER
import br.com.digital.store.components.strings.StringsUtils.WEDNESDAY
import br.com.digital.store.features.fee.domain.day.DayOfWeek
import br.com.digital.store.features.fee.domain.type.Function
import br.com.digital.store.features.fee.utils.AvailableDay
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

val daysOfWeek = listOf(
    AvailableDay(title = ALL, dayOfWeek = DayOfWeek.ALL),
    AvailableDay(title = SUNDAY, dayOfWeek = DayOfWeek.SUNDAY),
    AvailableDay(title = MONDAY, dayOfWeek = DayOfWeek.MONDAY),
    AvailableDay(title = TUESDAY, dayOfWeek = DayOfWeek.TUESDAY),
    AvailableDay(title = WEDNESDAY, dayOfWeek = DayOfWeek.WEDNESDAY),
    AvailableDay(title = THURSDAY, dayOfWeek = DayOfWeek.THURSDAY),
    AvailableDay(title = FRIDAY, dayOfWeek = DayOfWeek.FRIDAY),
    AvailableDay(title = SATURDAY, dayOfWeek = DayOfWeek.SATURDAY)
)

fun dayFactory(day: DayOfWeek? = null): String {
    return when (day) {
        DayOfWeek.ALL -> ALL
        DayOfWeek.SUNDAY -> SUNDAY
        DayOfWeek.MONDAY -> MONDAY
        DayOfWeek.TUESDAY -> TUESDAY
        DayOfWeek.WEDNESDAY -> WEDNESDAY
        DayOfWeek.THURSDAY -> THURSDAY
        DayOfWeek.FRIDAY -> FRIDAY
        DayOfWeek.SATURDAY -> SATURDAY
        null -> EMPTY_TEXT
    }
}

fun functionFactory(function: Function? = null): String {
    return when (function) {
        Function.ATTENDANT -> ATTENDANT
        Function.BOX -> BOX
        Function.WAITER -> WAITER
        null -> EMPTY_TEXT
    }
}
