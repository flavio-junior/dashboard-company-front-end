package br.com.digital.store.model.dto

data class SignUpResponseDTO(
    val name: String,
    val surname: String,
    val userName: String,
    val email: String,
    val password: String,
    val type: TypeAccount? = null,
    val telephone: String
)
