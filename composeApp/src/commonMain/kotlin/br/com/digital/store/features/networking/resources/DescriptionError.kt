package br.com.digital.store.features.networking.resources

data class DescriptionError(
    val code: Int? = null,
    val type: ErrorType,
    val message: String? = null
)
