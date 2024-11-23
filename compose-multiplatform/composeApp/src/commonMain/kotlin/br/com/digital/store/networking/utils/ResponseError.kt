package br.com.digital.store.networking.utils

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val status: Int,
    val message: String
)
