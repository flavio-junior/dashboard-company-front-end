package br.com.digital.store.features.report.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReportRequestDTO(
    var resume: String? = null,
    var author: String? = null
)
