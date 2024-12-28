package br.com.digital.store.components.strings

fun validateEmail(email: String): Boolean {
    val emailRegex = Regex(
        pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    )
    return emailRegex.matches(email)
}

fun CharSequence.isBlankAndEmpty(): Boolean {
    return isBlank() && isEmpty()
}

fun CharSequence.isNotBlankAndEmpty(): Boolean {
    return isNotBlank() && isNotEmpty()
}
