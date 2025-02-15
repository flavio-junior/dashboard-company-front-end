package br.com.digital.store.features.settings.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponseDTO(
    var status: Boolean = false,
    var id: Long? = 0,
    var version: String? = null,
    var url: String? = null
)
