package br.com.digital.store.features.networking.resources

data class DescriptionError(
    val code: Int? = null,
    val type: ErrorType = ErrorType.CLIENT,
    val message: String? = null
)
