package br.com.digital.store.features.networking.resources

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val status: Int,
    val message: String
)
