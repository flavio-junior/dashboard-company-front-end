package br.com.digital.store.utils

import kotlinx.coroutines.delay

suspend fun initializeWithDelay(
    time: Long,
    action: () -> Unit = {}
) {
    delay(timeMillis = time)
    action()
}
