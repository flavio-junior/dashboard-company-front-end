package br.com.digital.store.features.networking.utils

data class DescriptionError(
    val code: Int? = null,
    val type: ErrorType,
    val message: String? = null
)
