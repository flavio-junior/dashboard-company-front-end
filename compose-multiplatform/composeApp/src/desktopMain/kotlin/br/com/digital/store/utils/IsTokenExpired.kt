package br.com.digital.store.utils

import br.com.digital.store.utils.DesktopUtils.SIMPLE_DATE_EXPIRED_FORMAT
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun isTokenExpired(expirationDate: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern(SIMPLE_DATE_EXPIRED_FORMAT)
    val expirationInstance = LocalDateTime.parse(expirationDate, formatter)
    val currentDate = LocalDateTime.now()
    return currentDate.isAfter(expirationInstance)
}
