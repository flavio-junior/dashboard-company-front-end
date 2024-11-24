package br.com.digital.store.utils

actual fun getVersionDetails(): String {
    return BuildConfig.VERSION_NAME
}
