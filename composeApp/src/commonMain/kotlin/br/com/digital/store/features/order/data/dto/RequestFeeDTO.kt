package br.com.digital.store.features.order.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RequestFeeDTO(
    var assigned: String? = null
)
