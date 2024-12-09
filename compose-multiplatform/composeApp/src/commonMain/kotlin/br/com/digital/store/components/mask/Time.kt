package br.com.digital.store.components.mask

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatDate(originalDate: String): String {
    val date = Instant.parse(isoString = "${originalDate}Z")
        .toLocalDateTime(timeZone = TimeZone.UTC)
    return "${date.dayOfMonth.toString().padStart(length = 2, padChar = '0')}/${
        date.monthNumber.toString().padStart(length = 2, padChar = '0')
    }/${date.year} às ${date.hour.toString().padStart(length = 2, padChar = '0')}:${
        date.minute.toString().padStart(length = 2, padChar = '0')
    }:${date.second.toString().padStart(length = 2, padChar = '0')}"
}
