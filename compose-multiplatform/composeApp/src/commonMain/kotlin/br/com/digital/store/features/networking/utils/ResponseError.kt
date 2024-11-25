package br.com.digital.store.features.networking.utils

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val status: Int,
    val message: String
)
