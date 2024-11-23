package br.com.digital.store.networking.utils

fun errorResult(
    description: DescriptionError,
    message: (String) -> Unit = {},
    openErrorScreen: (Pair<String, String>) -> Unit = {}
) {
    when (description.type) {
        ErrorType.CLIENT -> message(description.message)
        ErrorType.INTERNAL -> openErrorScreen(Pair(description.type.name, description.message))
        ErrorType.EXTERNAL -> openErrorScreen(Pair(description.type.name, description.message))
        ErrorType.SERVER -> openErrorScreen(Pair(description.type.name, description.message))
    }
}
